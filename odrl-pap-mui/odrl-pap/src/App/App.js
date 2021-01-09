import React from "react";
import "./App.css";
import {
  makeStyles,
  Paper,
  ThemeProvider,
  unstable_createMuiStrictModeTheme as createMuiTheme ,
} from "@material-ui/core";
import Navigation from "../components/Navigation";
import { BrowserRouter as Router } from "react-router-dom";
import RouteToPage from "../components/Route";

const theme = createMuiTheme({
  palette: {
    primary: {
      main: "#179c7d",
    },
  },
});

const useStyles = makeStyles((theme) => ({
  seperator:{
  ...theme.mixins.toolbar,
  },
  content: {
    marginTop: '1em',
    marginLeft: '1em',
    marginRight:'1em',
  }
}));

function App() {
  const classes = useStyles();

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Navigation />
        <Paper className={classes.content}>
          <div className={classes.seperator}/>
          <RouteToPage />
        </Paper>
      </Router>
    </ThemeProvider>
  );
}

export default App;
