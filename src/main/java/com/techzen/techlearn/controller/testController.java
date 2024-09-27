package com.techzen.techlearn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("test")
public class testController {

    @GetMapping("base64")
    public void deCodeBase64(){
        String base64 = "cGFja2FnZSBjb20udGVjaHplbi50ZWNobGVhcm4uZHRvLnJlc3BvbnNlOwoK\n" +
                "aW1wb3J0IGNvbS5mYXN0ZXJ4bWwuamFja3Nvbi5hbm5vdGF0aW9uLkpzb25B\n" +
                "bnlHZXR0ZXI7CmltcG9ydCBjb20uZmFzdGVyeG1sLmphY2tzb24uYW5ub3Rh\n" +
                "dGlvbi5Kc29uQW55U2V0dGVyOwppbXBvcnQgY29tLmZhc3RlcnhtbC5qYWNr\n" +
                "c29uLmFubm90YXRpb24uSnNvbkluY2x1ZGU7CmltcG9ydCBsb21ib2suKjsK\n" +
                "aW1wb3J0IGxvbWJvay5leHBlcmltZW50YWwuRmllbGREZWZhdWx0czsKCmlt\n" +
                "cG9ydCBqYXZhLnV0aWwuRGF0ZTsKaW1wb3J0IGphdmEudXRpbC5IYXNoTWFw\n" +
                "OwppbXBvcnQgamF2YS51dGlsLk1hcDsKCkBHZXR0ZXIKQFNldHRlcgpAQnVp\n" +
                "bGRlcgpAQWxsQXJnc0NvbnN0cnVjdG9yCkBOb0FyZ3NDb25zdHJ1Y3RvcgpA\n" +
                "RmllbGREZWZhdWx0cyhsZXZlbCA9IEFjY2Vzc0xldmVsLlBSSVZBVEUpCkBK\n" +
                "c29uSW5jbHVkZShKc29uSW5jbHVkZS5JbmNsdWRlLk5PTl9OVUxMKQpwdWJs\n" +
                "aWMgY2xhc3MgUmVzcG9uc2VEYXRhPFQ+IHsKCiAgICBJbnRlZ2VyIHN0YXR1\n" +
                "czsKICAgIEBCdWlsZGVyLkRlZmF1bHQKICAgIEludGVnZXIgY29kZSA9IDEw\n" +
                "MDA7CiAgICBTdHJpbmcgbWVzc2FnZTsKICAgIFQgcmVzdWx0OwogICAgRGF0\n" +
                "ZSB0aW1lc3RhbXA7CiAgICBTdHJpbmcgcGF0aDsKICAgIFN0cmluZyBlcnJv\n" +
                "cjsKCiAgICBASnNvbkFueVNldHRlcgogICAgTWFwPFN0cmluZywgT2JqZWN0\n" +
                "PiBhZGRpdGlvbmFsUHJvcGVydGllcyA9IG5ldyBIYXNoTWFwPD4oKTsKCiAg\n" +
                "ICBwdWJsaWMgdm9pZCBhZGRpdGlvbmFsUHJvcGVydHkoU3RyaW5nIGtleSwg\n" +
                "T2JqZWN0IHZhbHVlKSB7CiAgICAgICAgdGhpcy5hZGRpdGlvbmFsUHJvcGVy\n" +
                "dGllcy5wdXQoa2V5LCB2YWx1ZSk7CiAgICB9CgogICAgQEpzb25BbnlHZXR0\n" +
                "ZXIKICAgIHB1YmxpYyBNYXA8U3RyaW5nLCBPYmplY3Q+IGdldEFkZGl0aW9u\n" +
                "YWxQcm9wZXJ0aWVzKCkgewogICAgICAgIHJldHVybiB0aGlzLmFkZGl0aW9u\n" +
                "YWxQcm9wZXJ0aWVzOwogICAgfQoKfQo=";
//        byte[] decodedBytes = Base64.getDecoder().decode(base64.replace("\\n",""));
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        String decodedString = new String(decodedBytes).replace("  "," ");
        System.out.println(decodedString);
    }

}
