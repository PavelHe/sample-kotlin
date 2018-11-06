package com.github.pavelvil.repostiory

import com.github.pavelvil.model.Link
import org.springframework.data.repository.CrudRepository


interface LinkRepository : CrudRepository<Link, Long> {
}