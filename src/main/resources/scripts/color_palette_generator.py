# we take color palette data from this website https://www.material-theme.com/docs/reference/color-palette/
# after that we parse the data to be compatible with javafx css.
palette_file_name = input("Enter the theme file name: ")
if not palette_file_name.endswith(".txt"):
    palette_file_name = palette_file_name + ".txt"
palette_file = open(palette_file_name, "r")
Lines = palette_file.readlines()

count = 0
prefix = "-theme-"
final_palette = ""
# Strips the newline character
for line in Lines:
    if len(line.strip()) == 0:
        continue
    count += 1
    print("Line{}: {}".format(count, line.strip()))
    parts = line.split(":")
    prop_name = parts[0]
    prop_name = prefix + prop_name.lower().replace(" ", "-").replace("/", "-")
    prop_color = parts[1]
    prop_color = prop_color.replace("\n", "")
    final_palette = final_palette + prop_name + ":" + prop_color + ";\n"
print(final_palette)
final_palette_file_name = palette_file_name.replace(".txt", "_final.txt")
final_palette_file = open(final_palette_file_name, "w")
final_palette_file.write(final_palette)
