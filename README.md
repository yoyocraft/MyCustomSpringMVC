# My Custom SpringMVC

> This project use custom DispatcherServlet to simulate the process of springMVC DispatcherServlet control Model and View.

## 主要实现的核心功能

- `DispatcherServlet`
- `Annotation`
  - `@Controller`
  - `@Service`
  - `@RequestMapping`
- Java Objects Autowired
- Controller Methods to get parameters
- View Resolver
- Return data in `Json` Format

## 实现流程简要分析

1. 开发`MyDispathcherServlet`
2. 实现客户端/浏览器可以请求控制层
3. 从`web.xml`中动态获取`myspringmvc.xml`数据
4. 完成Spring对象的自动装配`@Autowired`
5. 完成控制器方法获取参数`@RequestParam`
6. 完成简易的视图解析
7. 完成返回Json格式的数据`@RequestBody`

## 开发工具和环境

- Idea 2021.2.4
- Maven 3.6.1
- Jdk 1.8
- 原生ServletAPI
- Dom4j解析
- commons-lang3工具类
- Junit测试工具
