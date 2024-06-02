import java.util.Iterator;

import tr.edu.iyte.ceng112.binarysearchtree.BinarySearchTree;

public class Main {

	public static void main(String[] args) {
		
		FamilyMember emily = new FamilyMember("Emily", 1830, null);
		FamilyMember lily = new FamilyMember("Lily", 1840, null);
		FamilyMember olivia = new FamilyMember("Olivia", 1880, null);
		FamilyMember ethan = new FamilyMember("Ethan", 1915, null);
		FamilyMember mia = new FamilyMember("Mia", 1930, null);
		FamilyMember liam = new FamilyMember("Liam", 1970, null);
		FamilyMember ava = new FamilyMember("Ava", 2022, null);
		FamilyMember jack = new FamilyMember("Jack", 1890, null);
		
		FamilyMember david = new FamilyMember("David", 1845, new FamilyMember[] { emily, lily });
		FamilyMember sarah = new FamilyMember("Sarah", 1870, new FamilyMember[] { olivia });
		FamilyMember chloe = new FamilyMember("Chloe", 1920, new FamilyMember[] { ethan, mia });
		FamilyMember rachel = new FamilyMember("Rachel", 1978, new FamilyMember[] { liam });
		FamilyMember zoe = new FamilyMember("Zoe", 2015, new FamilyMember[] { ava });
		FamilyMember sophie = new FamilyMember("Sophie", 1910, new FamilyMember[] {chloe});
		
		FamilyMember jane = new FamilyMember("Jane", 1860, new FamilyMember[] { david, sarah });
		FamilyMember peter = new FamilyMember("Peter", 1900, new FamilyMember[] { jack, sophie });
		FamilyMember alex = new FamilyMember("Alex", 1987, new FamilyMember[] { rachel });
		FamilyMember ben = new FamilyMember("Ben", 2005, new FamilyMember[] { zoe });

		FamilyMember tom = new FamilyMember("Tom", 1985, new FamilyMember[] { jane, peter });
		FamilyMember kate = new FamilyMember("Kate", 1995, new FamilyMember[] { alex, ben });
		
		FamilyMember noah = new FamilyMember("Noah", 1935, new FamilyMember[] { tom });
		FamilyMember mary = new FamilyMember("Mary", 1960, new FamilyMember[] {kate });
		
		FamilyMember john = new FamilyMember("John", 1945, new FamilyMember[] { noah, mary });
		

		BinarySearchTree<FamilyMember> bst = new BinarySearchTree<>(john);

		bst.add(noah);
		bst.add(mary);
		bst.add(tom);
		bst.add(kate);
		bst.add(jane);
		bst.add(peter);
		bst.add(alex);
		bst.add(ben);
		bst.add(david);
		bst.add(sarah);
		bst.add(jack);
		bst.add(sophie);
		bst.add(rachel);
		bst.add(zoe);
		bst.add(emily);
		bst.add(lily);
		bst.add(olivia);
		bst.add(chloe);
		bst.add(liam);
		bst.add(ava);
		bst.add(ethan);
		bst.add(mia);
			
		System.out.println("INORDER");
		bst.inorderTraverse();
		Iterator<FamilyMember> inorderIterator = bst.getInorderIterator();
		printWithIterator(inorderIterator);

		
		System.out.println("PREORDER");
		bst.preorderTraverse();
		Iterator<FamilyMember> preorderIterator = bst.getPreorderIterator();
		printWithIterator(preorderIterator);
	
	
		System.out.println("POSTORDER");
		bst.postorderTraverse();
		Iterator<FamilyMember> postorderIterator = bst.getPostorderIterator();
		printWithIterator(postorderIterator);
		
		System.out.println("LEVELORDER");
		Iterator<FamilyMember> levelorderIterator = bst.getLevelOrderIterator();
		printWithIterator(levelorderIterator);

	}
	
	private static void printWithIterator(Iterator<FamilyMember> iterator) {

		System.out.println();
		while (iterator.hasNext()) {
			FamilyMember fm = iterator.next();
			System.out.print(fm);

		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

}
