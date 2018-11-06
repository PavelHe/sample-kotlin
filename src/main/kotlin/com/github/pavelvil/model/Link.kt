package com.github.pavelvil.model

import javax.persistence.*

@Entity
@Table(name = "links")
data class Link(
        val text: String = "",
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "links_sequence")
        @SequenceGenerator(name = "links_sequence", sequenceName = "links_seq")
        val id: Long = 0
)