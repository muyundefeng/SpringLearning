package com.designmodel.factory.impl;

import com.designmodel.entity.NoteBook;
import com.designmodel.entity.Pen;
import com.designmodel.entity.impl.GreenNoteBook;
import com.designmodel.entity.impl.GreenPen;
import com.designmodel.factory.ProductAbstractFactory;

/**一个具体的实现类，该类可以生产出一族产品，比如说该类可以生产出绿色相关的pen与notebook
 * Created by lisheng on 17-3-8.
 */
public class GreenProductFactoryImpl implements ProductAbstractFactory {
    @Override
    public Pen createPen() {
        return new GreenPen();
    }

    @Override
    public NoteBook createNoteBook() {
        return new GreenNoteBook();
    }
}
