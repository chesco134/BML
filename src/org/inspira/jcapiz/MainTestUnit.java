package org.inspira.jcapiz;

public class MainTestUnit {

	public static void main(String[] args) {
		// Levenston	Levenshtein	0.7272727272727273	0.5454545454545454
//		larder	lerder	0.8333333333333334	0.8333333333333334	0.6
//		Levenston	Levenshtein	0.5454545454545454	0.7272727272727273	0.5555555555555556
//		Jevenstoh	Levenshteir	0.4545454545454546	0.5454545454545454	0.4444444444444444
//		Morlow	Marlowes	0.625	0.625	0.5 
//		Levenston	Levenshtein
//		Morlow	Marlowes
//		Jevenstoh	Levenshteir
//		larder	lerder
//		larder	eiorder
		Character c1 = 'a';
		Character c2 = 'a';
		java.util.Map<Character, Integer> list = new java.util.TreeMap<>();
		list.put(c1, 1);
		list.put(c2, 2);
//		System.out.println(list.get('a'));
		Algorithms algs;
//		algs = new Algorithms("Tongomon", "Tangotooon");
//		System.out.println((double)algs.minDistance()/(double)"Tangotooon".length());
//		algs.strawberry();
//		System.out.println(algs.doEditInformatica());
		String[][] pairs = {
				{"Levenston","Levenshtein"},
				{"Morlow", "Marlowes"},
				{"Jevenstoh","Levenshteir"},
				{"larder","lerder"},
				{"larder","eiorder"},
				{"TUNNESL", "FUNNELS"},
				{"HOST","HOPS"},
				{"CRATE","TRACE"},
				{"391859","813995"}
				};
		for(int i=0; i<pairs.length; i++) {
			algs = new Algorithms(pairs[i][0], pairs[i][1]);
			System.out.println(pairs[i][0] + " " + pairs[i][1]);
			System.out.println("Hamming: " + algs.doHamming());
			System.out.println("Edit: " + algs.doEditInformatica());
			System.out.println("Bigram: " + algs.doBigramInformatica());
//			System.out.println("Jaro: " + algs.doJaroInformatica());
			System.out.println();
//			algs.blueberry();
		}
	}
}

//Levenston	Levenshtein	0.5454545454545454	0.7272727272727273	0.5555555555555556	0.872053872053872
//Morlow	Marlowes	0.625	0.625	0.5	0.6527777762876618
//Jevenstoh	Levenshteir	0.4545454545454546	0.5454545454545454	0.4444444444444444	0.5570947541145248
//larder	lerder	0.8333333333333334	0.8333333333333334	0.6	0.7222222207321062
//larder	eiorder	0.5714285714285714	0.5714285714285714	0.5454545454545454	0.28769840711047723