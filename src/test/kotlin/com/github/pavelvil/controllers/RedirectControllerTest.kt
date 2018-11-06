package com.github.pavelvil.controllers

import com.github.pavelvil.MicroserviceKotlinApplication
import com.github.pavelvil.service.KeyMapperService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
class RedirectControllerTest {

    private val PATH = "aAbBcCdD"
    private val REDIRECT_STATUS = 302
    private val HEADER_NAME = "Location"
    private val HEADER_VALUE = "http://www.eveonline.com"
    private val BAD_PATH = "mybadpath"
    private val NOT_FOUND = 404

    // для теста (контекст моего приложения с конмроллерами, которые я объявил)
    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: RedirectController

    // моковая переменная для тестирования
    lateinit var mockMvc: MockMvc

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this) //инициализация моков
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()

        //мокаю параметры, которые будут возвращаться в случае вызова service.getLink с входящими значениями
        Mockito.`when`(service.getLink(PATH)).thenReturn(KeyMapperService.Get.Link(HEADER_VALUE))
        Mockito.`when`(service.getLink(BAD_PATH)).thenReturn(KeyMapperService.Get.NotFound(BAD_PATH))
    }

    @Test
    fun controllerMustRedirectUsWhenRequestIsSuccessful() {
        // делаю запрос get и ожидаю получить редирект с указаным хедером
        mockMvc.perform(get("/$PATH"))
                .andExpect(status().`is`(REDIRECT_STATUS))
                .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_PATH"))
                .andExpect(status().`is`(NOT_FOUND))
    }

    @Test
    fun homeWorkFune() {
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
    }

}