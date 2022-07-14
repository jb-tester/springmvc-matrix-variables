package com.mytests.spring.springWebMatrixVariablesTest;

import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * *
 * <p>Created by irina on 7/13/2022.</p>
 * <p>Project: springmvc-matrix-variables</p>
 * *
 */
@RestController
public class MyRestController1 {


   // http://localhost:8080/matrix/t8/test8;start=0;first=1;second=2;last=3
    @GetMapping(value = "/matrix/t8/{foo:.*}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String test8(@MatrixVariable Map<String, String> data) {

        return String.format("Start: %s\nFirst: %s\nSecond: %s\nLast: %s\n",
                data.get("start"), data.get("first"), data.get("second"), data.get("last"));
    }

    // http://localhost:8080/matrix/t9/test9;bar=aaa;buzz=bbb
    @GetMapping(value = "/matrix/t9/{foo}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String test9(@PathVariable("foo") String pv,
                           @MatrixVariable("bar") String mv1,
                           @MatrixVariable("buzz") String mv2) {

        return String.format("foo: %s\nbar: %s\nbuzz: %s\n",
                pv, mv1, mv2);
    }
}
