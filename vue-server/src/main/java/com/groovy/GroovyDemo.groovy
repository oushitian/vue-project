import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

def test(str1,str2,str3) {
    printf(str1 + "  " + str2 + "  " + str3)

    def clos = {println " Hello ${it}"}
    clos.call(str1)

    String jsonString = "[\"wei.hu\",\"mengna.shi\",\"fastJson\"]"
    return JSON.parseObject(jsonString, new TypeReference<List<String>>() {})
}

test()
