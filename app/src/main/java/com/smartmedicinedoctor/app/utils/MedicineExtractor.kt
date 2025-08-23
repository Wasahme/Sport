package com.smartmedicinedoctor.app.utils

import com.smartmedicinedoctor.app.data.Medicine
import java.util.regex.Pattern

class MedicineExtractor {
    
    companion object {
        private val MEDICINE_PATTERNS = listOf(
            // Arabic patterns
            Pattern.compile("(\\w+)\\s*\\d+\\s*(ملغ|مجم|جرام|قرص|كبسولة|حقنة|شراب)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\w+)\\s*\\d+\\s*(مرة|مرات)\\s*(يومياً|في اليوم)", Pattern.CASE_INSENSITIVE),
            
            // English patterns
            Pattern.compile("(\\w+)\\s*\\d+\\s*(mg|g|tablet|capsule|injection|syrup)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\w+)\\s*\\d+\\s*(times|once|twice)\\s*(daily|per day)", Pattern.CASE_INSENSITIVE),
            
            // French patterns
            Pattern.compile("(\\w+)\\s*\\d+\\s*(mg|g|comprimé|gélule|injection|sirop)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\w+)\\s*\\d+\\s*(fois|une fois|deux fois)\\s*(par jour)", Pattern.CASE_INSENSITIVE)
        )
        
        private val DOSAGE_PATTERNS = listOf(
            Pattern.compile("(\\d+)\\s*(ملغ|مجم|جرام|قرص|كبسولة|حقنة|مل)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\d+)\\s*(mg|g|tablet|capsule|ml)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\d+)\\s*(mg|g|comprimé|gélule|ml)", Pattern.CASE_INSENSITIVE)
        )
        
        private val FREQUENCY_PATTERNS = listOf(
            Pattern.compile("(\\d+)\\s*(مرة|مرات)\\s*(يومياً|في اليوم|أسبوعياً)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\d+)\\s*(times|once|twice)\\s*(daily|per day|weekly)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\d+)\\s*(fois|une fois|deux fois)\\s*(par jour|par semaine)", Pattern.CASE_INSENSITIVE)
        )
        
        private val DURATION_PATTERNS = listOf(
            Pattern.compile("(\\d+)\\s*(يوم|أسبوع|شهر)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\d+)\\s*(days?|weeks?|months?)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\d+)\\s*(jours?|semaines?|mois)", Pattern.CASE_INSENSITIVE)
        )
    }
    
    fun extractMedicines(text: String, prescriptionId: Long): List<Medicine> {
        val medicines = mutableListOf<Medicine>()
        val lines = text.split("\n")
        
        for (line in lines) {
            val medicine = extractMedicineFromLine(line.trim(), prescriptionId)
            if (medicine != null) {
                medicines.add(medicine)
            }
        }
        
        return medicines
    }
    
    private fun extractMedicineFromLine(line: String, prescriptionId: Long): Medicine? {
        if (line.isBlank()) return null
        
        // Try to match medicine patterns
        for (pattern in MEDICINE_PATTERNS) {
            val matcher = pattern.matcher(line)
            if (matcher.find()) {
                val medicineName = matcher.group(1) ?: continue
                val dosage = extractDosage(line)
                val frequency = extractFrequency(line)
                val duration = extractDuration(line)
                val instructions = extractInstructions(line)
                
                return Medicine(
                    name = medicineName.trim(),
                    dosage = dosage,
                    frequency = frequency,
                    duration = duration,
                    instructions = instructions,
                    prescriptionId = prescriptionId,
                    extractedText = line
                )
            }
        }
        
        return null
    }
    
    private fun extractDosage(text: String): String {
        for (pattern in DOSAGE_PATTERNS) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
                return matcher.group(0) ?: ""
            }
        }
        return ""
    }
    
    private fun extractFrequency(text: String): String {
        for (pattern in FREQUENCY_PATTERNS) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
                return matcher.group(0) ?: ""
            }
        }
        return ""
    }
    
    private fun extractDuration(text: String): String {
        for (pattern in DURATION_PATTERNS) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
                return matcher.group(0) ?: ""
            }
        }
        return ""
    }
    
    private fun extractInstructions(text: String): String {
        // Extract additional instructions from the text
        val instructionKeywords = listOf(
            "قبل", "بعد", "مع", "على", "تحت", "فم", "عضل", "وريد",
            "before", "after", "with", "on", "under", "oral", "intramuscular", "intravenous",
            "avant", "après", "avec", "sur", "sous", "oral", "intramusculaire", "intraveineuse"
        )
        
        val words = text.split(" ")
        val instructions = mutableListOf<String>()
        
        for (word in words) {
            if (instructionKeywords.any { word.contains(it, ignoreCase = true) }) {
                instructions.add(word)
            }
        }
        
        return instructions.joinToString(" ")
    }
}