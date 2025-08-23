package com.smartmedicinedoctor.app.repository

import com.smartmedicinedoctor.app.data.*
import kotlinx.coroutines.flow.Flow

class MedicineRepository(
    private val prescriptionDao: PrescriptionDao,
    private val medicineDao: MedicineDao,
    private val medicineReminderDao: MedicineReminderDao
) {
    
    // Prescription operations
    fun getAllPrescriptions(): Flow<List<Prescription>> {
        return prescriptionDao.getAllPrescriptions()
    }
    
    suspend fun getPrescriptionById(id: Long): Prescription? {
        return prescriptionDao.getPrescriptionById(id)
    }
    
    suspend fun insertPrescription(prescription: Prescription): Long {
        return prescriptionDao.insertPrescription(prescription)
    }
    
    suspend fun updatePrescription(prescription: Prescription) {
        prescriptionDao.updatePrescription(prescription)
    }
    
    suspend fun deletePrescription(prescription: Prescription) {
        prescriptionDao.deletePrescription(prescription)
    }
    
    // Medicine operations
    fun getMedicinesByPrescriptionId(prescriptionId: Long): Flow<List<Medicine>> {
        return medicineDao.getMedicinesByPrescriptionId(prescriptionId)
    }
    
    suspend fun getMedicineById(id: Long): Medicine? {
        return medicineDao.getMedicineById(id)
    }
    
    suspend fun insertMedicine(medicine: Medicine): Long {
        return medicineDao.insertMedicine(medicine)
    }
    
    suspend fun insertMedicines(medicines: List<Medicine>) {
        medicineDao.insertMedicines(medicines)
    }
    
    suspend fun updateMedicine(medicine: Medicine) {
        medicineDao.updateMedicine(medicine)
    }
    
    suspend fun deleteMedicine(medicine: Medicine) {
        medicineDao.deleteMedicine(medicine)
    }
    
    suspend fun searchMedicines(query: String): List<Medicine> {
        return medicineDao.searchMedicines(query)
    }
    
    // Medicine Reminder operations
    fun getAllReminders(): Flow<List<MedicineReminder>> {
        return medicineReminderDao.getAllReminders()
    }
    
    fun getActiveReminders(): Flow<List<MedicineReminder>> {
        return medicineReminderDao.getActiveReminders()
    }
    
    suspend fun getReminderById(id: Long): MedicineReminder? {
        return medicineReminderDao.getReminderById(id)
    }
    
    suspend fun insertReminder(reminder: MedicineReminder): Long {
        return medicineReminderDao.insertReminder(reminder)
    }
    
    suspend fun updateReminder(reminder: MedicineReminder) {
        medicineReminderDao.updateReminder(reminder)
    }
    
    suspend fun deleteReminder(reminder: MedicineReminder) {
        medicineReminderDao.deleteReminder(reminder)
    }
    
    suspend fun updateReminderStatus(id: Long, isActive: Boolean) {
        medicineReminderDao.updateReminderStatus(id, isActive)
    }
    
    // Complex operations
    suspend fun deletePrescriptionWithMedicines(prescription: Prescription) {
        // Delete all medicines associated with this prescription
        medicineDao.deleteMedicinesByPrescriptionId(prescription.id)
        // Delete all reminders associated with medicines from this prescription
        val medicines = medicineDao.getMedicinesByPrescriptionId(prescription.id).first()
        medicines.forEach { medicine ->
            medicineReminderDao.deleteRemindersByMedicineId(medicine.id)
        }
        // Delete the prescription
        prescriptionDao.deletePrescription(prescription)
    }
    
    suspend fun deleteMedicineWithReminders(medicine: Medicine) {
        // Delete all reminders associated with this medicine
        medicineReminderDao.deleteRemindersByMedicineId(medicine.id)
        // Delete the medicine
        medicineDao.deleteMedicine(medicine)
    }
}