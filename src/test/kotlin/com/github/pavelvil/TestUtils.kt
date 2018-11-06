package com.github.pavelvil

import org.mockito.Mockito

fun <T> whenever(call: T) = Mockito.`when`(call)
