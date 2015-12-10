package com.example.samsisekki.algorithm.entity;

import com.example.samsisekki.algorithm.impl.Cosine;
import com.example.samsisekki.algorithm.similarity.ComputeSimilarity;
import com.example.samsisekki.algorithm.similarity.Distance;
import com.example.samsisekki.algorithm.util.VectorUtil;

import java.util.BitSet;

public class Vector implements Distance {
	private float[] vector;
	private float size;
	private int length;
	private BitSet bitSet;
	private ComputeSimilarity computeSimilarity = new Cosine();

	public Vector(float[] vector) {
		this.vector = vector;
		this.length = vector.length;
		this.size = VectorUtil.computeVectorSize(vector);
		this.bitSet = VectorUtil.getIndexsOfNonzero(vector);
	}

	public float[] getVector() {
		return vector;
	}

	public void setVector(float[] vector) {
		this.vector = vector;
	}

	public int getLength() {
		return length;
	}

	public float getSize() {
		return size;
	}

	public BitSet getBitSet() {
		return bitSet;
	}

	public float valueOf(int index) {
		return this.vector[index];
	}

	public void setComputeSimilarity(ComputeSimilarity computeSimilarity) {
		this.computeSimilarity = computeSimilarity;
	}
	public float distanceTo(Vector vectorOther) {
		return computeSimilarity.compute(this, vectorOther);
	}
}