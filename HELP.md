# Matrix Variables


Note that for Spring Boot > 2.5.8 the URLs processing is changed:
thus, with SB 2.5.8 the following mapping method

```

    @RequestMapping(value = "/matrix/t2/{mvId}", method = RequestMethod.GET)
    public String matrixVarsTest2(@MatrixVariable Integer m_v, ModelMap model) {..}
 }
```
will work if we provide request like follows:

```
GET http://localhost:8080/matrix/t2/m_v=100
```
but with SB version 2.6+ teh path variables are read only starting with ';', so we need somethong like

```
GET http://localhost:8080/matrix/t2/smth;m_v=100
```