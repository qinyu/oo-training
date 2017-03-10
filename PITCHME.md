
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
[《设计模式：可复用面向对象软件的基础》](https://book.douban.com/subject/1052241/)  
![book_cover](https://img3.doubanio.com/lpic/s1074361.jpg)     
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

# 单例模式

+++

## 实现
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

## 最短的线程安全的实现
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

## 运用

[👿为什么单例模式是邪恶的](http://www.cnblogs.com/nomoneynowife/p/3719031.html)  <!-- .element: class="fragment" -->
* 和全局变量没什么区别<!-- .element: class="fragment" --> 可能导致内存泄漏 <!-- .element: class="fragment" -->   
* 即负责创建逻辑也限制创建的数量，违反SRP<!-- .element: class="fragment" --> 
<!--工厂模式与建造者模式不违反SRP-->
* 调用的地方紧耦合，违反OCP<!-- .element: class="fragment" --> 无法在测试时方便的替换 <!-- .element: class="fragment" -->
* 全局状态处于未知状态<!-- .element: class="fragment" --> 测试无法独立运行 <!-- .element: class="fragment" -->

✨仔细分析上下文，确定无上诉设计的需要(确定是全局仅需要一个实例); 或者改用其他设计模式 <!-- .element: class="fragment" -->

---

# 简单工厂模式

把创建分离到单独的方法

+++ 

## 实现(待续)
```java
public class SimplePizzaFactory {
public Pizza createPizza(String type) {
Pizza pizza = null;
if (type.equals(“cheese”)) { pizza = new CheesePizza();
} else if (type.equals(“pepperoni”)) { pizza = new PepperoniPizza();
} else if (type.equals(“clam”)) {
pizza = new ClamPizza();
} else if (type.equals(“veggie”)) {
pizza = new VeggiePizza(); }
 return pizza; }
}
```

+++

## 实现(完)
```java
public class SimplePizzaFactory {
    SimplePizzaFactory factory;
    public PizzaStore(SimplePizzaFactory factory) { this.factory = factory; }
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        if (type.equals(“cheese”)) { pizza = new CheesePizza(); }
        else if (type.equals(“pepperoni”)) { pizza = new PepperoniPizza(); }
        else if (type.equals(“clam”)) { pizza = new ClamPizza(); } else if (type.equals(“veggie”)) { pizza = new VeggiePizza(); }
        return pizza;
    }
}
```

## 支持

IDE: https://www.jetbrains.com/help/idea/2016.3/replace-constructor-with-factory-method.html

---

# 工厂方法模式

提供了一个把生成逻辑移交给子类的方法。

+++

## 实现

```java
interface Interviewer {
    public void askQuestions();
}

public class Developer implements Interviewer {
    public void askQuestions() {
        System.out.prinln("Asking about design patterns!");
    }
}

public class CommunityExecutive implements Interviewer {
    public void askQuestions() {
        System.out.prinln("Asking about community building");
    }
}
```

---

# 抽象工厂模式

一个制造工厂的工厂；一个工厂把独立但是相关／有依赖性的工厂进行分类，但是不需要给出具体的类。

+++

## 实现(待续)
```java
public interface Shape {
   void draw();
}
public class Rectangle implements Shape {
   @Override
   public void draw() {
      System.out.println("Inside Rectangle::draw() method.");
   }
}
public class Square implements Shape {

   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}
```
+++

## 实现(待续)
```java
public interface Color {
   void fill();
}
public class Red implements Color {

   @Override
   public void fill() {
      System.out.println("Inside Red::fill() method.");
   }
}
public class Green implements Color {

   @Override
   public void fill() {
      System.out.println("Inside Green::fill() method.");
   }
}
public class Blue implements Color {

   @Override
   public void fill() {
      System.out.println("Inside Blue::fill() method.");
   }
}
```

## 实现(完)

```java
public abstract class AbstractFactory {
   abstract Color getColor(String color);
   abstract Shape getShape(String shape) ;
}
```

---

# 建造者模式

创建不同特点的对象而避免构造函数污染。当一个对象都多种特点的时候比较实用。或者在创造逻辑里有许多步骤的时候。

+++

## 👎[伸缩套管式构造函数](http://codethataint.com/blog/telescoping-constructor-pattern-java/)
```java
Pizza(int size) { ... }        
Pizza(int size, boolean cheese) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni, boolean bacon) { ... }
```
#### 👎解决方法之一
```java
Pizza pizza = new Pizza(12);
pizza.setCheese(true);
pizza.setPepperoni(true);
pizza.setBacon(true);
```
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

- Android: [ShareCompat.IntentBuilder](https://developer.android.com/reference/android/support/v4/app/ShareCompat.IntentBuilder.html) [NotificationCompat.Builder](https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html)
- IDE支持：https://www.jetbrains.com/help/idea/2016.3/replace-constructor-with-builder.html

---

# 原型模式

通过"克隆"已有的对象来创建新对象

+++

## 实现(待续)
```java
    /**
     * Retrieves a map of extended data from the intent.
     *
     * @return the map of all extras previously added with putExtra(),
     * or null if none have been added.
     */
    public Bundle getExtras() {
        return (mExtras != null)
                ? new Bundle(mExtras)
                : null;
    }
```

+++

## 实现(待续)
```java
public final class Bundle extends BaseBundle implements Cloneable, Parcelable {
    /**
     * Constructs a Bundle containing a copy of the mappings from the given
     * Bundle.
     *
     * @param b a Bundle to be copied.
     */
    public Bundle(Bundle b) {
        super(b);
        mFlags = b.mFlags;
    }

    /**
     * Clones the current Bundle. The internal map is cloned, but the keys and
     * values to which it refers are copied by reference.
     */
    @Override
    public Object clone() {
        return new Bundle(this);
    }
}
```

+++

# 实现(完)

```java
public class BaseBundle {
/**
     * Constructs a Bundle containing a copy of the mappings from the given
     * Bundle.
     *
     * @param b a Bundle to be copied.
     */
    BaseBundle(BaseBundle b) {
        if (b.mParcelledData != null) {
            if (b.isEmptyParcel()) {
                mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
            } else {
                mParcelledData = Parcel.obtain();
                mParcelledData.appendFrom(b.mParcelledData, 0, b.mParcelledData.dataSize());
                mParcelledData.setDataPosition(0);
            }
        } else {
            mParcelledData = null;
        }

        if (b.mMap != null) {
            mMap = new ArrayMap<>(b.mMap);
        } else {
            mMap = null;
        }

        mClassLoader = b.mClassLoader;
    }
}
```

+++

## 依赖注入

