package com.designmodel.factory;

import com.designmodel.entity.NoteBook;
import com.designmodel.entity.Pen;

/**
 * Created by lisheng on 17-3-8.
 */
public interface ProductAbstractFactory {
    Pen createPen();
    NoteBook createNoteBook();
}
