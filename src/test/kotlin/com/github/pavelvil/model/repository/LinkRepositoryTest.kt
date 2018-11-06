package com.github.pavelvil.model.repository

import com.github.pavelvil.model.AbstractRepositoryTest
import com.github.pavelvil.model.Link
import com.github.pavelvil.repostiory.LinkRepository
import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import org.hamcrest.Matchers.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import org.junit.Assert.*

@DatabaseSetup(LinkRepositoryTest.DATASET) //передаю место, где лежит БД. оттуда берётся xml и бд заполняется данными отуда
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = [(LinkRepositoryTest.DATASET)]) //когда бд будет ложиться, то будем стерать все данные
open class LinkRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var repository: LinkRepository

    @Test
    fun findOneExisting() {
        val got: Optional<Link> = repository.findById(LINK_ONE_ID)
        assertThat(got.isPresent, equalTo(true))
        val link = got.get()
        assertThat(link, equalTo(Link(LINK_ONE_TEXT, LINK_ONE_ID)))
    }

    @Test
    fun findOneNotExisting() {
        val got: Optional<Link> = repository.findById(LINK_NOT_FOUND_ID)
        assertThat(got.isPresent, equalTo(false))
    }

    @Test
    fun saveNew() {
        val toBeSaved = Link(LINK_TBS_TEXT)
        val got: Link = repository.save(toBeSaved)
        val links: List<Link> = repository.findAll().toList()
        assertThat(links, hasSize(4))
        assertThat(got.text, equalTo(LINK_TBS_TEXT))
    }

    companion object{
        const val DATASET = "classpath:datasets/link-table.xml"
        private val LINK_ONE_ID: Long = 100500L
        private val LINK_ONE_TEXT: String = "http://www.eveonline.com"
        private val LINK_TBS_TEXT: String = "http://www.ru"
        private val LINK_NOT_FOUND_ID: Long = 1L
    }

}