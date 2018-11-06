package com.github.pavelvil.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.pavelvil.MicroserviceKotlinApplication
import com.github.pavelvil.service.KeyMapperService
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [(MicroserviceKotlinApplication::class)])
@TestPropertySource(locations = ["classpath:/repositories-test.properties"])
@SpringBootTest
class AddControllerTest {

    private val KEY: String = "key"
    private val LINK: String = "link"

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: AddController

    lateinit var mockMvc: MockMvc

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()

        Mockito.`when`(service.add(LINK)).thenReturn(KEY)
    }

    @Test
    fun whenUserAddLinkHeTakeAKey() {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(AddController.AddRequest(LINK))))
                .andExpect(jsonPath("$.link", equalTo(LINK)))
                .andExpect(jsonPath("$.key", equalTo(KEY))) // в поле key должен содержаться KEY
    }

    //проверка в addController addht,l
    @Test
    fun whenUserAddLinkByFormHeTakesAWebPage() {
        mockMvc.perform(post("/addhtml")
                .param("link", LINK) //передаваемый параметр в метод (RequestParam)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk) // ожидаю статус OK
                .andExpect(content().string(containsString(KEY))) //ожидаю, что в конетнте будет строка KEY
                .andExpect(content().string(containsString(LINK)))
    }

}