package com.github.masooh.intellij.plugin.groovyfier

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

// no virtual file

class ConvertJavaToGroovyTest : LightJavaCodeInsightFixtureTestCase() {

    override fun getTestDataPath() = "src/test/resources/testdata"

    fun testName() {
        myFixture.configureByFile("BookTest.java")
        myFixture.testAction(ConvertJavaToGroovy())
        myFixture.checkResultByFile("BookTest.groovy")
    }
}