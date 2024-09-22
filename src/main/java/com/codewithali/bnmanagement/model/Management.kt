package com.codewithali.bnmanagement.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Management(
    @Id
    val id: String? = null,
    val caseNumber: Int,
    val operatorAdi: String,
    val firmaAdi: String,
    val vakaIlcesi: String,
    val vakaSehri: String,
    val hizmet: String,
    val mesafe: Int,
    val ekPoz: String,
    val documentUrl: String,
    val date: LocalDate,
    val ilceler: String,
    val pozSayisi: Int,
    val vakaSonlandiran: String,
    val aciklama: String
)
