package optimized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * This class optimized & extend Render Class the optimization only for
 * heavyweight function optimization use the threading method
 * 
 * @author meerz
 *
 */
public class PictureThreading extends Render {
	private int _cores;
	private ExecutorService threadPull;
	private RndrImgRun _rnImRun[];

	public PictureThreading(Scene s, ImageWriter im) {
		super(s, im);
		_cores = Runtime.getRuntime().availableProcessors();
		threadPull = Executors.newFixedThreadPool(_cores);
		_rnImRun = new RndrImgRun[_cores];
	}

	//
	/*
	 * renderImage function optimized by threading
	 */
	@Override
	public void renderImage() {
		ImageWriter tmpImW = this.get_imageWriter();
		int nX = tmpImW.getNx(), nY = tmpImW.getNy();
		int nXTreadPart = nX / _cores;
		int nYTreadPart = nY / _cores;
		for (int i = 0; i < _cores; i++) {
			_rnImRun[i] = new RndrImgRun(nXTreadPart * i, nYTreadPart * i, nXTreadPart * (i + 1), nYTreadPart * (i + 1),
					this.get_scene(), this.get_imageWriter());
		}
		for (RndrImgRun rndrImgRun : _rnImRun) {
			threadPull.submit(rndrImgRun);
		}
	}

	public int getCores() {
		return _cores;

	}
}
