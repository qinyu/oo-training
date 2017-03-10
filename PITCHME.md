
# 面向对象设计原则

❓有哪些面向对象编程的概念 <!-- .element: class="fragment" -->

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

* **封装**-把一个单元的内部实现隐藏起来，显示尽量少的接口，使变化局限于单元内部 
* **继承**-使用现有类的一种方式，子类可以直接复用父类的属性和行为 
* **代理**-使用现有类的另一种方式，将请求转派给已有类的对象 
* **多态**-不同类型的对象能响应同一个方法调用，并表现出各自不同的行为 

❓重要程度如何排序 <!-- .element: class="fragment" -->

<!--
封装->委托->继承
继承的问题
-->

---

# 应对变化和复杂

---

# 高内聚&低耦合

* **内聚**是一个系统里多个部分一起执行工作的度量，来获得比每个部分单独工作获得更好的结果 
* **耦合**是一个类、方法或者任何一个实体直接与另一个实体连接的度 

---

# SOLID原则 
[《敏捷软件开发 原则、模式与实践》](https://book.douban.com/subject/1140457/)    
![book_cover](https://img3.doubanio.com/lpic/s1671095.jpg)    
作者: Robert C. Martin   
[✨一些软件设计的原则](http://coolshell.cn/articles/4535.html) <!-- .element: class="fragment" -->

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

---

## 设计模式
[《设计模式：可复用面向对象软件的基础》](https://zh.wikipedia.org/wiki/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%EF%BC%9A%E5%8F%AF%E5%A4%8D%E7%94%A8%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1%E8%BD%AF%E4%BB%B6%E7%9A%84%E5%9F%BA%E7%A1%80)  
![book_cover](https://upload.wikimedia.org/wikipedia/en/7/78/Design_Patterns_cover.jpg)     
作者(GoF): Gamma Erich, Helm Richard, Johnson Ralph, Vlissides John.

---

## 设计模式分类

![](http://www.vincehuston.org/images/GoF_full_medium.png)  

**创建型模式**<!-- .element: class="fragment" --> **行为型模式**<!-- .element: class="fragment" --> **结构型模式**<!-- .element: class="fragment" --> **架构模式**<!-- .element: class="fragment" -->

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

+++

## 常见实现
```java
public class ToolManager {
    private static volatile ToolManager mToolManager;
    private static Object mObject = new Object();

    public static ToolManager getInstance(){
        if (mToolManager == null){
            synchronized (mObject) {
                if (mToolManager == null) {
                    mToolManager = new ToolManager();
                }
            }
        }
        return mToolManager;
    }
}
```
❓为什么要这样实现   <!-- .element: class="fragment" -->
<!--延迟加载的线程不安全，synchronized带来性能开销，双重检查锁定必须使用volatile(java 1.5)
-->  
[双重检查锁定与延迟初始化](http://www.infoq.com/cn/articles/double-checked-locking-with-delay-initialization) <!-- .element: class="fragment" -->

+++

## 最短的线程安全实现
```java
public class InstanceFactory {
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        return InstanceHolder.instance;
    }
}
```

## 单例模式的问题

[👿为什么单例模式是邪恶的](http://www.cnblogs.com/nomoneynowife/p/3719031.html)  <!-- .element: class="fragment" -->
* 和全局变量没什么区别 <!-- .element: class="fragment" -->
* 即负责创建逻辑也限制创建的数量，违反SRP <!-- .element: class="fragment" --> 
<!--工厂模式与建造者模式不违反SRP-->
* 紧耦合，违反OCP，无法在测试时方便的替换 <!-- .element: class="fragment" -->
* 无法判断当前的状态，测试无法独立运行 <!-- .element: class="fragment" -->

✨仔细分析上下文，确定无上诉设计的需要; 或者改用其他设计模式 <!-- .element: class="fragment" -->
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

## 👎[望远镜构造函数](http://codethataint.com/blog/telescoping-constructor-pattern-java/)
```java
Pizza(int size) { ... }        
Pizza(int size, boolean cheese) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni, boolean bacon) { ... }
```

+++

## 👎解决方法

+++

## 👍解决方法(建造者)
```java
public class Pizza {
  private int size;
  private boolean cheese;
  private boolean pepperoni;
  private boolean bacon;
 
  public static class Builder {
    //required
    private final int size;
 
    //optional
    private boolean cheese = false;
    private boolean pepperoni = false;
    private boolean bacon = false;
 
    public Builder(int size) {
      this.size = size;
    }
 
    public Builder cheese(boolean value) {
      cheese = value;
      return this;
    }
    ...
    public Pizza build() {
      return new Pizza(this);
    }
  }
 
  private Pizza(Builder builder) {
    size = builder.size;
    cheese = builder.cheese;
    pepperoni = builder.pepperoni;
    bacon = builder.bacon;
  }
}
...
Pizza pizza = new Pizza.Builder(12)
                       .cheese(true)
                       .pepperoni(true)
                       .bacon(true)
                       .build();
```
## 运用

- IDE支持：https://www.jetbrains.com/help/idea/2016.3/replace-constructor-with-builder.html
+++

## 


## 原型模式

![uml](https://www.tutorialspoint.com/design_pattern/images/prototype_pattern_uml_diagram.jpg)

+++



+++

## 依赖注入

