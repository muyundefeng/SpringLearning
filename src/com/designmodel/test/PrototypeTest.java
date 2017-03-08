package com.designmodel.test;

import com.designmodel.entity.Person;
import com.designmodel.prototype.CloneObj;
import com.designmodel.prototype.DeepClone;
import com.designmodel.prototype.PrototypeClone;
import com.designmodel.prototype.ShallowClone;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lisheng on 17-3-8.
 */
public class PrototypeTest {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        CloneObj cloneObj = new CloneObj();
        CloneObj cloneObj1 = cloneObj.clone();
        System.out.println(cloneObj==cloneObj1);

        PrototypeClone prototypeClone = new PrototypeClone();
        PrototypeClone prototypeClone1 = prototypeClone.clone();
        System.out.println(prototypeClone == prototypeClone1);

        //浅复制
        ShallowClone shallowClone = new ShallowClone();
        shallowClone.setList(new ArrayList<>());
        ShallowClone shallowClone1 = shallowClone.clone();
        System.out.println(shallowClone == shallowClone1);
        System.out.println(shallowClone.getList() == shallowClone1.getList());

        //深复制
        DeepClone deepClone = new DeepClone();
        deepClone.setPerson(new Person());
        DeepClone deepClone1 = deepClone.deepClone();
        System.out.println(deepClone1 == deepClone);
        System.out.println(deepClone1.getPerson()==deepClone.getPerson());
    }
}
