
# 面向对象设计原则

❓有哪写面向对象编程的概念 <!-- .element: class="fragment" -->

+++

# 标题
## 标题
### 标题
#### 标题
##### 标题
###### 标题

很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的正文

> 很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的引用

---

## 面向对象术语

* **封装**-把一个单元的内部实现隐藏起来，显示尽量少的接口，使变化局限于单元内部 <!-- .element: class="fragment" -->
* **继承**-使用现有类的一种方式，子类可以直接复用父类的属性和行为 <!-- .element: class="fragment" -->
* **代理**-使用现有类的另一种方式，将请求转派给已有类的对象 <!-- .element: class="fragment" -->
* **多态**-不同类型的对象能响应同一个方法调用，并表现出各自不同的行为 <!-- .element: class="fragment" -->

❓重要程度如何排序 <!-- .element: class="fragment" -->

<!--
封装->委托->继承
继承的问题
-->

---

# 应对变化和复杂

---

# 高内聚&低耦合

* **内聚**是一个系统里多个部分一起执行工作的度量，来获得比每个部分单独工作获得更好的结果 <!-- .element: class="fragment" -->
* **耦合**是一个类、方法或者任何一个实体直接与另一个实体连接的度 <!-- .element: class="fragment" -->

---

# SOLID原则 

✨一些软件设计的原则 <!-- .element: class="fragment" -->  
http://coolshell.cn/articles/4535.html <!-- .element: class="fragment" -->

+++

## 单一职责原则(Single Responsibility)

一个类只能因为一个原因改变

<!--
代码重复，不同“方向”的变化
-->

+++

## 开闭原则(Open/Close)

软件实体(类、方法，对象)对修改封闭，对扩展开放

<!--
面向接口编程，组合由于继承(LSP)
-->

+++

## 里氏替换原则(Liskov's Substitution)

子类不能破坏父类的类型定义

<!--
继承关系的开闭原则，契约
-->

+++

## 接口隔离原则(Interface Segregation)

客户不应该被强迫依赖它用不上的方法

+++

## 依赖倒置原则(Dependency Inversion)

A. 上层模块不应该依赖下层模块，两者之间应该依赖抽象而不是具体实现
B. 抽象不应该依赖于细节，细节应该依赖于抽象

---?image=http://www.vincehuston.org/images/GoF_full_medium.png

## 设计模式

* 创建型模式 <!-- .element: class="fragment" -->
* 行为型模式 <!-- .element: class="fragment" -->
* 结构型模式 <!-- .element: class="fragment" -->
* 架构模式 <!-- .element: class="fragment" -->

---

## 创建型模式

* 单例模式
* 工厂方法模式
* 抽象工厂模式
* 建造者模式
* 原型模式
* 依赖注入模式
---

## 单例模式

![uml](https://www.tutorialspoint.com/design_pattern/images/singleton_pattern_uml_diagram.jpg)


---

## 工厂模式

![uml](https://www.tutorialspoint.com/design_pattern/images/factory_pattern_uml_diagram.jpg)

+++

## 抽象工厂模式
![uml](https://www.tutorialspoint.com/design_pattern/images/abstractfactory_pattern_uml_diagram.jpg)

+++

## 建造者模式

![uml](https://www.tutorialspoint.com/design_pattern/images/builder_pattern_uml_diagram.jpg)

+++

## [望远镜构造函数](http://codethataint.com/blog/telescoping-constructor-pattern-java/)
```java
Pizza(int size) { ... }        
Pizza(int size, boolean cheese) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni, boolean bacon) { ... }
```

+++
## 解决方法
+++
## 解决方法二
```
Pizza pizza = new Pizza.Builder(12)
                       .cheese(true)
                       .pepperoni(true)
                       .bacon(true)
                       .build();
```
## 运用


+++

## 


## 原型模式

![uml](https://www.tutorialspoint.com/design_pattern/images/prototype_pattern_uml_diagram.jpg)

+++



+++

## 依赖注入

