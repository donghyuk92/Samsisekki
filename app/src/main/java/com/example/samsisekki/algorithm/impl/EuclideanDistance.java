package com.example.samsisekki.algorithm.impl;

import com.example.samsisekki.algorithm.entity.Vector;
import com.example.samsisekki.algorithm.similarity.ComputeSimilarity;
import com.example.samsisekki.algorithm.util.VectorUtil;

public class EuclideanDistance implements ComputeSimilarity {

	public float compute(Vector vector1, Vector vector2) {
		VectorUtil.validateVectorLength(vector1, vector2);
		int vectorLength = vector1.getLength();
		float quadraticSum = 0;
		for(int i = 0; i < vectorLength; i++) {
			float minus = vector1.valueOf(i) - vector2.valueOf(i);
			float minusSquare = (float) Math.pow(minus, 2);
			quadraticSum += minusSquare;
		}
		return (float) Math.sqrt(quadraticSum);
	}
}
