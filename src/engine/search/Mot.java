package engine.search;

public class Mot {
		private String mot;
		private int frequence;
		public Mot(int frequence) {
			mot = null;
			this.frequence = frequence;
		}
		public String getMot() {
			return mot;
		}
		public void setMot(String mot) {
			this.mot = mot;
		}
		public int getFrequence() {
			return frequence;
		}
		public void setFrequence(int frequence) {
			this.frequence = frequence;
		}
	}