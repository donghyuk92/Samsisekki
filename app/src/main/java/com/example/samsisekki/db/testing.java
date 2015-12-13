package com.example.samsisekki.db;

import com.example.samsisekki.algorithm.entity.Vector;
import com.example.samsisekki.algorithm.util.VectorUtil;

import java.util.ArrayList;

public class testing {

	public static void main(String[] args) {
		float[] asd= new float[5];
        asd[0] = (float) 2.9;
        asd[1] = (float) 15.3;
        asd[4] = 4.9999f;
        Vector vc = new Vector(asd);
        
        float[] qwe= new float[5];
        qwe[0] = (float) 999.9;
        qwe[1] = (float) 2.3;
        qwe[2] = (float) 3.9;
        qwe[3] = 2.35f;
        Vector vc1 = new Vector(qwe);
      
        float[] zxc= new float[5];
   //     zxc[0] = (float) 5.9;
        zxc[1] = (float) 22.3;
  //      zxc[2] = (float) 10.9;
        zxc[3] = 1.35f;
    //    zxc[4] = 15.35f;
        
        Vector vc2 = new Vector(zxc);
        
        float[] a= new float[5];
        a[0] = (float) 3.1;
        a[1] = (float) 12.3;
        a[2] = (float) 1699.9;
        a[3] = 123.12f;
        a[4] = 123.1f;
        Vector vc3 = new Vector(a);
        

        
        ArrayList<Vector> tmp = new ArrayList<Vector>();
        tmp.add(vc);
        tmp.add(vc1);
        tmp.add(vc2);
        tmp.add(vc3);
        
        int k = 0;

        int index1 = VectorUtil.getRecommended(tmp, k);
        System.out.println("index1 = " + index1);
        
        System.out.println("\n\n");
        
        Vector likeme = VectorUtil.getSimilarVec(tmp, k); 
        // Vector Like me
        Vector origin = tmp.get(k);
        // Vector of origin
        ArrayList<Integer> tmp1 = VectorUtil.getDifferList(origin, likeme);
        // Compare origin vector with likeme vector, result in int arraylist
        int index = VectorUtil.getMenuIndex(likeme, tmp1);
        // Extract recommended menu index using differlist
        System.out.println("index = " + index);
	}
	
//        Random randomizer = new Random();
  //      Integer random = rr.get(randomizer.nextInt(rr.size()));
    //    System.out.println(random);

}
