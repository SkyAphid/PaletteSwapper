# PaletteSwapper - The easy to use sprite recoloring tool
![rock example](https://user-images.githubusercontent.com/6147299/144668165-7cc80c1c-402a-485f-8dd6-642be9f11fb4.png)

PaletteSwapper is a small tool that allows you to recolor images and sprites quickly using basic syntax in an ordinary text file. 
- Can make multiple recolors at once and export multiple files just using one syntax .txt file
- It supports both indexed images, but also unindexed images as well. Outputted images are indexed for you.

![Screenshot 2021-11-04 151440](https://user-images.githubusercontent.com/6147299/140413136-810b3385-3b26-4987-a479-d4abc3da63f8.png)

## Examples
When you run Palette Swapper, it will first ask for a text file containing instructions for the recoloring, and then a list of images to recolor (multiple image files can be selected). Palette Swapper requires you to make a basic .txt file (*color swapping definitions*), so that it knows what colors to replace and how many different recolored files to make (*chunks*).

### [Syntax Example File](https://github.com/SkyAphid/PaletteSwapper/blob/master/PaletteSwapper/RockPalette.txt)

```
#4D5F64
#69837B
#93AA9A
#3F474E
-
#776242
#A88960
#C1B588
#4F382F
```
This is the example syntax file included with the project. You can click the link above to view and download it as well. These files must be saved as .txt files.

***Step-by-step tutorial:***
- At the top of the file, insert the base colors to be replaced that are in the file you wish to recolor.
- Indicate a split (or chunk) with a ``-``. Splits indicate a new file, and you can add as many splits and files as you want.
- Add the corresponding colors below that you'd want the base colors at the top replaced with. Just make sure the orders of your new colors correspond appropriately to the base colors.
- Run the program and the new recolored files will be produced in the same location as the original file.

***Further information***

These .txt definitions take in HEX values. So you'll list out the base colors that you wish to replace (as in, the palette of the image you're starting with), and then add a split and list out the HEX values of the corresponding replacement colors. You must list these out in the same order, so in the example above, the first color at the top is `#4D5F64`; then after the first split (the `-`), the first value is `#776242`. That means when PaletteSwapper is ran with this definition, all instances of color `#4D5F64` will be replaced with the color `#776242`.

## [Releases / Download](https://github.com/SkyAphid/PaletteSwapper/releases)
You can download a standalone EXE for the program here. The JAR is included, so you can run it with JRE16 or higher from command line as well if you're on another OS.
