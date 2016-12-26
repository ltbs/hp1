with import <nixpkgs> {}; {
  yetanotherbrowser = stdenv.mkDerivation {
    name = "yetanotherbrowser";
    buildInputs = [ stdenv oraclejdk scala sbt ffmpeg ];
    shellHook = ''
      export PS1="\n\[\033[1;32m\][\[\033[1;34m\]$name\[\033[1;32m\]:\w]$\[\033[0m\] "
      alias sbt="sbt -java-home $JAVA_HOME"
    '';
  };
}
