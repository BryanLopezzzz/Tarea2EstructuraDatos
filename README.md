```

## Uso del Programa

El proyecto incluye un archivo de prueba `hola.txt` listo para usar. El programa permite comprimir archivos de texto a formato `.huf` y descomprimirlos de vuelta a su formato original.

### Comprimir un archivo
1. Seleccionar opción `1` en el menú
2. Ingresar la ruta del archivo a comprimir (ej: `hola.txt`)
3. Ingresar la ruta del archivo de salida (ej: `hola.huf`)

### Descomprimir un archivo
1. Seleccionar opción `2` en el menú
2. Ingresar la ruta del archivo comprimido (ej: `hola.huf`)
3. Ingresar la ruta del archivo de salida (ej: `hola_recuperado.txt`)

## Manejo de Archivos

El programa crea automáticamente los archivos de salida especificados. Si el archivo de salida ya existe, será sobrescrito. Esto permite reutilizar nombres de archivos si es necesario.

**Importante:** El proceso de compresión y descompresión es completamente reversible y no destructivo. Aunque puedes sobrescribir archivos usando el mismo nombre para entrada y salida, el contenido original se puede recuperar completamente mediante la descompresión del archivo `.huf`. El algoritmo de Huffman garantiza que descomprimir un archivo comprimido produce exactamente el mismo contenido que el archivo original, sin pérdida de información.

### Ejemplo de flujo completo
```
1. Comprimir: hola.txt → hola.huf
2. Descomprimir: hola.huf → hola_nuevo.txt
   Resultado: hola_nuevo.txt es idéntico a hola.txt original
3. O puedes usar el Comprimir y Descomprimir en el mismo hola.txt