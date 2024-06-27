const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

// Viteでビルドを実行する
execSync('vite build', { stdio: 'inherit' });

// ビルドされたファイルの出力ディレクトリのパス (デフォルトは 'dist')
const buildOutputDir = path.resolve(__dirname, 'dist');

// コピー先ディレクトリの定義
const webappPath = path.resolve(__dirname, 'src/main/webapp');
const scriptsPath = path.join(webappPath, 'scripts');
const stylesPath = path.join(webappPath, 'styles');
const fontsPath = path.join(stylesPath, 'fonts');

// build.js を webappPath/scripts にコピーする
const jsFile = path.join(buildOutputDir, 'build.js');
const scriptsDest = path.join(scriptsPath, 'build.js');
fs.copyFileSync(jsFile, scriptsDest);
console.log(`build.js を ${scriptsPath} にコピーしました。`);

// fonts ディレクトリ内のファイルをコピーする
const fontsDir = path.join(buildOutputDir, 'fonts');
fs.readdirSync(fontsDir).forEach(file => {
    const srcPath = path.join(fontsDir, file);
    if (file.endsWith('.css')) {
        const destPath = path.join(stylesPath, file); // styles にコピー
        fs.copyFileSync(srcPath, destPath);
        //console.log(`${file} を ${stylesPath} にコピーしました。`);
    } else {
        const destPath = path.join(fontsPath, file); // fonts ディレクトリにコピー
        fs.copyFileSync(srcPath, destPath);
        //console.log(`${file} を ${fontsPath} にコピーしました。`);
    }
});

// cssファイルをコピーする
const cssSrcPath = path.resolve(__dirname, 'src/main/vue/styles');
const cssDestPath = path.resolve(__dirname, 'src/main/webapp/styles');
fs.copyFileSync(path.join(cssSrcPath, 'open-iconic-bootstrap.min.css'), path.join(cssDestPath, 'open-iconic-bootstrap.min.css'));

console.log(`ビルドが完了し、ファイルが ${webappPath} にコピーされました。`);
