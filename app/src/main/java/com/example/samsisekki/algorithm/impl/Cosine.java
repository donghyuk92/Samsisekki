package com.example.samsisekki.algorithm.impl;

import com.example.samsisekki.algorithm.entity.Vector;
import com.example.samsisekki.algorithm.similarity.ComputeSimilarity;
import com.example.samsisekki.algorithm.util.VectorUtil;

public class Cosine implements ComputeSimilarity {

	public float compute(Vector vector1, Vector vector2) {
		float dotProduc = VectorUtil.dotProduct(vector1, vector2);
		float vector1Size = VectorUtil.computeVectorSize(vector1);
		float vector2Size = VectorUtil.computeVectorSize(vector2);
		float consine = dotProduc / (vector1Size * vector2Size);
		if (consine > 1) {
			consine = 1;
		}
		return consine;
	}

}
