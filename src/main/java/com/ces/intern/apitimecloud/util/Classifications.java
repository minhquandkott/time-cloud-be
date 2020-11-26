package com.ces.intern.apitimecloud.util;
import org.springframework.core.io.ClassPathResource;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.*;

import java.io.File;
import java.io.InputStream;

public class Classifications {

//    private static String approveModel = new File("").getAbsolutePath()+""+"\\data\\approve.model";
//    private static String bugModel = new File("").getAbsolutePath()+""+"\\data\\bug.model";;
//    private static String featureModel = new File("").getAbsolutePath()+""+"\\data\\feature.model";
    private static InputStream[] getPathModel () {
        InputStream bug =null, feature=null, approve=null;
        try {
            bug = new ClassPathResource("bug.model").getInputStream();
            feature = new ClassPathResource("feature.model").getInputStream();
            approve = new ClassPathResource("approve.model").getInputStream();

        }catch (Exception e){
            e.printStackTrace();
        }
        return new InputStream[]{bug, feature, approve};
    }

    private static int Classify(InputStream path,String discussion) throws Exception {
        FilteredClassifier ft = (FilteredClassifier) SerializationHelper.read(path);
        FastVector fvNominalVal = new FastVector(2);
        fvNominalVal.addElement("0");
        fvNominalVal.addElement("1");
        Attribute attribute1 = new Attribute("class-att", fvNominalVal);
        Attribute attribute2 = new Attribute("text",(FastVector) null);
        // Create list of instances with one element
        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(attribute2);
        fvWekaAttributes.addElement(attribute1);
        Instances instances = new Instances("Test relation", fvWekaAttributes, 1);
        // Set class index
        instances.setClassIndex(1);
        // Create and add the instance
        DenseInstance instance = new DenseInstance(2);
        //Put your discussion here
        instance.setValue(attribute2, discussion);
        // instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
        instances.add(instance);
        double pred = ft.classifyInstance(instances.instance(0));
        //System.out.println("Class predicted: " + instances.classAttribute().value((int) pred));

        return Integer.parseInt(instances.classAttribute().value((int) pred));
    }

    public static Integer classifyType(String discussion) throws Exception {

        InputStream[] listURL = getPathModel();
        for(int i = 0; i < listURL.length; i++){
            if(Classify(listURL[i],discussion) == 1){
                return i;
            }
        }
        return 3;
    }
}
