package com.mytests.spring.springWebMatrixVariablesTest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * *
 * <p>Created by irina on 7/13/2022.</p>
 * <p>Project: springmvc-matrix-variables</p>
 * *
 */
@Controller
public class MyViewController1 {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    // http://localhost:8080/matrix/t0/100;mv=50
    // segment template is mapped to path variable
    @GetMapping("/matrix/t0/{pv}")
    public String matrixVarsTest0(@PathVariable String pv, @MatrixVariable String mv, Model model) {
        System.out.println("pv : " + pv);
        System.out.println("mv: " + mv);
        model.addAttribute("pathVariableValue", pv);
        model.addAttribute("matrixVarValue", mv);

        return "mvt0";
    }

    // segment template is mapped to @MatrixVariable#pathVar
    // http://localhost:8080/matrix/t1/smth;matrix_var=mv_value
    @GetMapping("/matrix/t1/{foo}")
    public String matrixVarsTest1(@MatrixVariable(name = "matrix_var", pathVar = "foo", defaultValue = "something") String mvar, Model model) {
        System.out.println("matrix variable: " + mvar);
        model.addAttribute("matrixVarValue", mvar);

        return "mvt1";
    }

    // segment template is not mapped
    // http://localhost:8080/matrix/t2/smth;m_v=100
    @RequestMapping(value = "/matrix/t2/{mvId}", method = RequestMethod.GET)
    public String matrixVarsTest2(@MatrixVariable Integer m_v, ModelMap model) {
        System.out.println("mv: " + m_v);
        model.addAttribute("provided_matrix_var", m_v);
        return "mvt2";
    }

    // multiple matrix variables binded to multiple segment templates
    // http://localhost:8080/matrix/t3/smth;key1=100;key2=foo/smth;key3=bar;key4=buzz
    @RequestMapping(value = "/matrix/t3/{aaa}/{bbb}", method = RequestMethod.GET)
    public String matrixVarsTest3(@MatrixVariable(pathVar = "aaa") Map<String, String> aaa,
                                  @MatrixVariable(pathVar = "bbb") Map<String, String> bbb, Model model) {
        model.addAttribute("aaa", aaa);
        model.addAttribute("bbb", bbb);
        return "mvt3";

    }
    // partial binding: matrix variable binded to one of vars in one of multiple segment templates
    // http://localhost:8080/matrix/t4/smth;key1=100;key2=foo/smth;key1=bar;key2=buzz
    @RequestMapping(value = "/matrix/t4/{ccc}/{ddd}", method = RequestMethod.GET)
    public String matrixVarsTest4(@MatrixVariable(pathVar = "ddd", name = "key2") String mv, Model model) {
        model.addAttribute("matrix_variable_value", mv);
        return "mvt4";

    }

    // multiple segment paths, matrix variables mapping by name
    // http://localhost:8080/matrix/t5/smth;first=foo/smth;last=bar
    @GetMapping(value = "/matrix/t5/{yyy}/{xxx}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String matrixVarsTest5(@MatrixVariable("first") String first,
                                  @MatrixVariable("last") String last, Model model) {
        model.addAttribute("last", last);
        model.addAttribute("first", first);
        return "mvt5";
    }
    // invalid mapping: more than one match for URI path parameter 'mv'. Use 'pathVar' attribute to disambiguate.
    // http://localhost:8080/matrix/t6/smth;mv=foo/smth;mv=bar
    @RequestMapping(value = "/matrix/t6/{ccc}/{ddd}", method = RequestMethod.GET)
    public String matrixVarsTest6(@MatrixVariable String mv, Model model) {
        model.addAttribute("matrix_variable_value", mv);
        return "error";

    }

    // invalid mapping: no path variable in mapping
    // http://localhost:8080/matrix/t7/smth;mv=foo
    @RequestMapping(value = "/matrix/t7", method = RequestMethod.GET)
    public String matrixVarsTest7(@MatrixVariable String mv, Model model) {
        model.addAttribute("matrix_variable_value", mv);
        return "error";

    }
}
