package com.github.pavelvil.model

import com.github.pavelvil.MicroserviceKotlinApplication
import com.github.springtestdbunit.DbUnitTestExecutionListener
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests

//поднимает контексты для тестов
@TestExecutionListeners(DbUnitTestExecutionListener::class)
@ContextConfiguration(classes = [(MicroserviceKotlinApplication::class)])
@TestPropertySource(locations = ["classpath:/repositories-test.properties"]) //переопределение главного application.properties на тестовый
@DirtiesContext
abstract class AbstractRepositoryTest : AbstractTransactionalJUnit4SpringContextTests() {
}