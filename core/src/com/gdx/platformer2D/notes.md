
Get Width/Height

```
int width = Gdx.graphics.getHeight()
int height = Gdx.graphics.getWidth()
```

Draw text

```
fnt.draw(batch, Float.toString(Gdx.graphics.getDeltaTime()), x, y);
```

Draw Texture

```
batch.begin();
batch.draw(img, x, y);
batch.end();
```