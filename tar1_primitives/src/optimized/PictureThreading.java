package optimized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import renderer.ImageWriter;
import scene.Scene;

/**
 * This class optimized & extend Render Class the optimization only for
 * heavyweight function optimization use the threading method
 * 
 * @author meerz
 *
 */
public class PictureThreading {
	private int _cores;
	private ExecutorService threadPull;
	private RndrImgRun _rnImRun[];
	Scene _sc;

	public Scene get_sc() {
		return _sc;
	}

	public ImageWriter get_im() {
		return _im;
	}

	ImageWriter _im;

	public PictureThreading(Scene s, ImageWriter im) {
		_sc = new Scene(s);
		_im = new ImageWriter(im);
		_cores = Runtime.getRuntime().availableProcessors();
		threadPull = Executors.newFixedThreadPool(_cores);
		_rnImRun = new RndrImgRun[_cores];
	}

	//
	/*
	 * renderImage function optimized by threading
	 */
	public void renderImage() {
		ImageWriter tmpImW = this.get_im();
		int nX = tmpImW.getNx(), nY = tmpImW.getNy();
		int nXTreadPart = nX / _cores;
		int nYTreadPart = nY / _cores;
		for (int i = 0; i < _cores; i++) {
			_rnImRun[i] = new RndrImgRun(nXTreadPart * i, nYTreadPart * i, nXTreadPart * (i + 1), nYTreadPart * (i + 1),
					this.get_sc(), this.get_im());
		}
		for (RndrImgRun rndrImgRun : _rnImRun) {
			threadPull.submit(rndrImgRun);
		}
	}

	public int getCores() {
		return _cores;

	}
}
