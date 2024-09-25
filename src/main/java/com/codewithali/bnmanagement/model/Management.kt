package com.codewithali.bnmanagement.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate

@Document
data class Management @JvmOverloads constructor(
    @Id
    val id: String? = null,
    val caseNumber: Int,
    val operatorAdi: String,
    val firmaAdi: String,
    val vakaIlcesi: String,
    val vakaSehri: String,
    val hizmet: String,
    val mesafe: Int,
    val projeFitat: BigDecimal? = null,
    val projeFiyatTotal: BigDecimal? = null,
    val pozBirimFiyat: BigDecimal,
    val kmBirimFiyat: BigDecimal,
    val total: BigDecimal,
    val ekPoz: Int? = null,
    val documentUrl: String? = null,
    val date: LocalDate,
    val pozSayisi: Int? = null,
    val vakaSonlandiran: String,
    val aciklama: String? = null
)
