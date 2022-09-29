import React from "react";
import "./App.css";
import {
  makeStyles,
  ThemeProvider,
  AppBar,
  unstable_createMuiStrictModeTheme as createMuiTheme,
} from "@material-ui/core";
import Navigation from "../components/Navigation";
import { HashRouter as Router } from "react-router-dom";

import RouteToPage from "../components/Route";
import clsx from "clsx";
import CssBaseline from '@material-ui/core/CssBaseline';
import history from "../components/history";

const drawerWidth = 324;

const theme = createMuiTheme({
  palette: {
    primary: {
      main: "#239b7e",
      light: "#E8F5F2",
    },
    secondary: {
      main: "#E96A22",
    },
    background: {
      main: "#F2F2F2",
    },
    text: {
      primary: "#808080",
    },
  },
  overrides: {
    MuiButton: {
      root: {
        borderRadius: 100,
        "&:hover": {
          color: "#F7C7AC",
        },
      },
    },
    MuiTypography: {
      subtitle2: {
        color: "#808080",
      },
      h1: {
        color: "#E96A22",
        fontFamily: "FrutigerLTComBold",
        fontSize: "27px",
      },
    },

  },
});

const useStyles = makeStyles((theme) => ({
  content: {
    paddingTop: 35,
    marginLeft: drawerWidth + 0,
    marginTop: 80 + 0,
    paddingBottom: 35,
    minHeight: "calc(100vh - 115px)",
  },
}));

function App() {
  const [open, setOpen] = React.useState(window.innerWidth > 600);
  const classes = useStyles();
  return (
    <ThemeProvider theme={theme}>
      <Router history={history}>
        <CssBaseline />

        <Navigation open={open} setOpen={setOpen} />
        <AppBar position="fixed" />
        <div
          className={clsx(classes.appBar, {
            [classes.appBarShift]: open,
          })}
        >
          <div className={classes.content}>
            <RouteToPage />
          </div>
        </div>
      </Router>
    </ThemeProvider>
  );
}

export default App;
