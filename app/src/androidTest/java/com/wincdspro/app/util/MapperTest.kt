package com.wincdspro.app.util

import com.wincdspro.app.model.wincds.core.Cash
import junit.framework.Assert.assertNotNull
import org.junit.Test

class MapperTest {
    @Test
    fun testMapperJson() {
        assertNotNull(Mapper.fromJson("{}", Cash::class.java))
    }
}
