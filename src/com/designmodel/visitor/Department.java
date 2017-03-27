package com.designmodel.visitor;

/**
 * 定义抽象访问者
 * <p>
 * 在软件开发中，有时候我们也需要处理像处方单这样的集合对象结构，在该对象结构中存储了多个不同类型的对象信息，
 * 而且对同一对象结构中的元素的操作方式并不唯一，可能需要提供多种不同的处理方式，还有可能增加新的处理方式。
 * <p>
 * 访问者模式是一种较为复杂的行为型设计模式，它包含访问者和被访问元素两个主要组成部分，
 * 这些被访问的元素通常具有不同的类型，且不同的访问者可以对它们进行不同的访问操作。
 * 在使用访问者模式时，被访问元素通常不是单独存在的，它们存储在一个集合中，这个集合被称为“对象结构”，
 * 访问者通过遍历对象结构实现对其中存储的元素的逐个操作。
 * <p>
 * Vistor（抽象访问者）：抽象访问者为对象结构中每一个具体元素类ConcreteElement声明一个访问操作，从这个操作的名称或参数类型可以清楚知道需要访问的具体元素的类型，具体访问者需要实现这些操作方法，定义对这些元素的访问操作。
 * ●ConcreteVisitor（具体访问者）：具体访问者实现了每个由抽象访问者声明的操作，每一个操作用于访问对象结构中一种类型的元素。
 * ●Element（抽象元素）：抽象元素一般是抽象类或者接口，它定义一个accept()方法，该方法通常以一个<h1>抽象访问者作为参数</h1>。【稍后将介绍为什么要这样设计。】
 * ●ConcreteElement（具体元素）：具体元素实现了accept()方法，在accept()方法中调用访问者的访问方法以便完成对一个元素的操作。
 * ● ObjectStructure（对象结构）：对象结构是一个元素的集合，它用于存放元素对象，并且提供了遍历其内部元素的方法。它可以结合组合模式来实现，也可以是一个简单的集合对象，如一个List对象或一个Set对象。
 * <p>
 * <p>
 * (1) 调用具体元素类的accept(Visitor visitor)方法，并将Visitor子类对象作为其参数；
 * (2) 在具体元素类accept(Visitor visitor)方法内部调用传入的Visitor对象的visit()方法，如visit(ConcreteElementA elementA)，将当前具体元素类对象(this)作为参数，如visitor.visit(this)；
 * (3) 执行Visitor对象的visit()方法，在其中还可以调用具体元素对象的业务方法。
 * <p>
 * 定义抽象访问者
 * Created by lisheng on 17-3-27.
 */
public abstract class Department {
    //声明一组重载的访问方法，用于访问不同类型的具体元素
    public abstract void visit(FulltimeEmployee employee);

    public abstract void visit(ParttimeEmployee employee);
}
