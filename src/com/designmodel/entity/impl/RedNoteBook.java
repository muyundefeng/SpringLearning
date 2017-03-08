package com.designmodel.entity.impl;

import com.designmodel.entity.NoteBook;

/**
 * Created by lisheng on 17-3-8.
 */
public class RedNoteBook implements NoteBook {
    @Override
    public void printName() {
        System.out.println("this is RedNotebook");
    }
}
