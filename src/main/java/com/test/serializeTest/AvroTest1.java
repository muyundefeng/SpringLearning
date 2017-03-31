package com.test.serializeTest;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.util.Utf8;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by lisheng on 17-3-31.
 */
public class AvroTest1 {
    public static void main(String[] args) throws IOException {
        Schema.Parser parser = new Schema.Parser();//加载scheme模式
        Schema schema = parser.parse(new FileInputStream("/home/lisheng/work/workspace/javaspace/AvroTest/src/main/resources/array.avsc"));

        //创建一个符合上述scheme的实例
        GenericRecord daum = new GenericData.Record(schema);
        daum.put("left", new Utf8("L"));
        daum.put("right",new Utf8("R"));

        //将上述实例序列化到输出流中
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
        Encoder encoder = EncoderFactory.get().binaryEncoder(out,null);
        writer.write(daum,encoder);
        encoder.flush();
        out.close();

        //将相关对象序列化到文件中
        File file = new File("/home/lisheng/work/workspace/javaspace/AvroTest/src/main/resources/array.avro");
        DatumWriter<GenericRecord> writer1 = new GenericDatumWriter<GenericRecord>();
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(writer1);
        dataFileWriter.create(schema,file);//写入文件时候需要指定相关的模式
        dataFileWriter.append(daum);
        dataFileWriter.close();

        //将文件中序列化存储的对象读取出来
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>();
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file,datumReader);
        //读取文件的时候不需要指定相关的模式
        System.out.println(dataFileReader.getSchema().getField("right"));
        System.out.println(dataFileReader.getSchema().getField("left").toString());

        //模式解析，读写模式可以不同
        //还是本利，将上面的模式增加一个字段，在读取模式的时候可以正常运行
//        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>();
//        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file,datumReader);
//        //读取文件的时候不需要指定相关的模式
//        System.out.println(dataFileReader.getSchema().getField("right"));
//        System.out.println(dataFileReader.getSchema().getField("left").toString());
//        System.out.println(dataFileReader.getSchema().getField("description"));//增加的额外字段，
        //正如上面所示，读取已经增加字段模式文件可以正常工作，同样也可以减少一些字段也能正常工作。

        //排列顺序

    }
}
