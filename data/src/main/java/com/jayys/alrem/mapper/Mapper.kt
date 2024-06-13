package com.jayys.alrem.mapper

interface Mapper<From, To> {
    fun From.mapToDomainModel(): To
    fun To.mapFromDomainModel(): From
}