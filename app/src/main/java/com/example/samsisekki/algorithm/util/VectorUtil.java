package com.example.samsisekki.algorithm.util;

import com.example.samsisekki.algorithm.entity.Vector;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VectorUtil {

    public static void validateVectorLength(Vector vector1, Vector vector2) {
        int vectorLength = vector1.getLength();
        if (vectorLength != vector2.getLength()) {
            throw new IllegalArgumentException("vector1's length"
                    + vectorLength + "is not equal to the vector2's "
                    + "length:" + vector2.getLength());
        }
    }

    public static float computeVectorSize(Vector vector) {
        float[] vector1 = vector.getVector();
        return computeVectorSize(vector1);
    }

    public static float computeVectorSize(float[] vector) {
        int vectorLength = vector.length;
        float quadraticSum = 0;
        for (int i = 0; i < vectorLength; i++) {
            float element = vector[i];
            float elementSquare = (float) Math.pow(element, 2);
            quadraticSum += elementSquare;
        }
        return (float) Math.sqrt(quadraticSum);
    }

    public static float dotProduct(Vector vector1, Vector vector2) {
        BitSet bitSet1 = vector1.getBitSet();
        BitSet bitSet2 = vector2.getBitSet();
        bitSet1.and(bitSet2);
        int vectorLength = vector1.getLength();
        float sum = 0f;
        for (int i = 0; i < vectorLength; i++) {
            if (bitSet1.get(i)) {
                float product = vector1.valueOf(i) * vector2.valueOf(i);
                sum += product;

            }
        }
        return sum;
    }

    public static BitSet getIndexsOfNonzero(Vector vector) {
        return getIndexsOfNonzero(vector.getVector());
    }

    public static BitSet getIndexsOfNonzero(float[] vector) {
        int vectorLength = vector.length;
        BitSet bitSet = new BitSet(vectorLength);
        for (int i = 0; i < vectorLength; i++) {
            if (vector[i] != 0) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    public static BitSet copyBitSet(BitSet bitSet) {
        int bitsetLength = bitSet.length();
        BitSet copyBitSet = new BitSet();
        for (int i = 0; i < bitsetLength; i++) {
            if (bitSet.get(i)) {
                copyBitSet.set(i);
            }
        }
        return copyBitSet;
    }

    public static int getNumofNonzero(BitSet bitSet) {
        int num = 0;
        int bitsetLength = bitSet.length();
        for (int i = 0; i < bitsetLength; i++) {
            if (bitSet.get(i)) {
                num++;
            }
        }
        return num;
    }

    public static float getMean(Vector vector) {
        float[] vc = vector.getVector();
        int num = getNumofNonzero(getIndexsOfNonzero(vc));
        float sum = 0;
        for (float item : vc)
            sum += item;
        return sum / (float) num;
    }

    public static float getVar(Vector vector) {
        float[] vc = vector.getVector();
        int num = getNumofNonzero(getIndexsOfNonzero(vc));
        float mean = getMean(vector);
        float errsum = 0;
        for (float item : vc) {
            if (item != 0) {
                float err = item - mean;
                errsum += err * err;
            }
        }
        return errsum / num;
    }

    public static float getWeight(Vector vector) {
        return 0f;
    }

    public static Vector getSimilarVec(ArrayList<Vector> VectorList, int who) {
        int k = who;
        Vector originvec = VectorList.get(k);
        float[] origin = getNormalVec(originvec);
        Vector nomalorg = new Vector(origin);
        float distance = -1f;
        int order = 0;
        for (int i = 0; i < VectorList.size(); i++) {
            if (i != k) {
                float[] nomalized = getNormalVec(VectorList.get(i));
                Vector nomalvec = new Vector(nomalized);
                float res = nomalorg.distanceTo(nomalvec);
                if (distance < res) {
                    distance = res;
                    order = i;
                }
            }
        }
        return VectorList.get(order);
    }

    public static ArrayList<Integer> getDifferList(Vector origin, Vector likeme) {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        float[] test = origin.getVector();
        float[] test2 = likeme.getVector();
        for (int i = 0; i < likeme.getLength(); i++) {
            if (test[i] == 0 && test2[i] != 0)
                tmp.add(i);
        }
        return tmp;
    }

    public static int getMenuIndex(Vector likeme, ArrayList<Integer> candidate) {
        float[] test = likeme.getVector();
        int index = 0;
        float rating = 0f;
        for (int i = 0; i < candidate.size(); i++) {
            if (test[candidate.get(i)] > rating) {
                rating = test[i];
                index = candidate.get(i);
            }
        }
        return index;
    }

    public static float[] getNormalVec(Vector vector) {
        float[] origin = vector.getVector();
        float[] res = new float[vector.getLength()];
        float mean = getMean(vector);
        float var = getVar(vector);
        for (int i = 0; i < res.length; i++)
            if (origin[i] != 0)
                res[i] = (origin[i] - mean) / var;
        return res;
    }

    public static java.util.Vector getRecommended(ArrayList<Vector> VectorList, int who) {
        Vector likeme = getSimilarVec(VectorList, who);
        // Vector Like me
        Vector origin = VectorList.get(who);
        // Vector of origin
        ArrayList<Integer> tmp = getDifferList(origin, likeme);
        // Compare origin vector with likeme vector, result in int arraylist
        int index = getMenuIndex(likeme, tmp);
        // Extract recommended menu index using differlist
        java.util.Vector vec = new java.util.Vector();
        vec.add(0,tmp);
        vec.add(1,index);
        return vec;
    }
}
