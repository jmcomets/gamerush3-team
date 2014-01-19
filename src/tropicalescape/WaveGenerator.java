package tropicalescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.geom.Vector2f;

public class WaveGenerator{
	
		private int NbMaxWaves = 50;
		private static double proba = 25;
		
		private static final String[] IMG_FILES = { 
			"res/animations/waves/vagues-decor1.png",
			"res/animations/waves/vagues-decor2.png",
			"res/animations/waves/vagues-decor3.png",
			};
		private static final String[] empty_string = {};
		
		
		public List<GameObject> generateWaves() {
			ArrayList<GameObject> list = new ArrayList<GameObject>();
			Random rand = new Random();
			int counter = 0;
			while ((rand.nextInt(100) > proba) &&  (counter < this.NbMaxWaves) ){
				GameObject obj = new GameObject(HitboxAnimationFactory.create(IMG_FILES, empty_string, 500)){
					@Override 
					public boolean isAlive(){
						return true;
						}
					};
				list.add(obj);
				obj.setPosition(new Vector2f(rand.nextInt(1024), rand.nextInt(576)));
				counter++;
			}
			return list;
		}
	}


