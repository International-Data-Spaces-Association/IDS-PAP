cd src/main/ui/
if not exist "node_modules\" (START /B /wait "" npm install)
npm start
PAUSE