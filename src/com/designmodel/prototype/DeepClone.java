package com.designmodel.prototype;
import  com.designmodel.entity.Person;

import java.io.*;

/**
 * 深复制
 * Created by lisheng on 17-3-8.
 */
public class DeepClone implements Serializable {

    private static final long serialVersionUID = 3969438177161438988L;

    public Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public DeepClone deepClone() throws IOException, ClassNotFoundException, OptionalDataException {
        //将对象写入流中
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        //将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (DeepClone) ois.readObject();

    }
}
