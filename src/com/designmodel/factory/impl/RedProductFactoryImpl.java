package com.designmodel.factory.impl;

import com.designmodel.entity.NoteBook;
import com.designmodel.entity.Pen;
import com.designmodel.entity.impl.RedNoteBook;
import com.designmodel.entity.impl.RedPen;
import com.designmodel.factory.ProductAbstractFactory;

/**
 * Created by lisheng on 17-3-8.
 */
public class RedProductFactoryImpl implements ProductAbstractFactory {
    @Override
    public Pen createPen() {
        return new RedPen();
    }

    @Override
    public NoteBook createNoteBook() {
        return new RedNoteBook();
    }
}
