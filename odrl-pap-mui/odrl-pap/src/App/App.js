import React from "react";
import "./App.css";
import {
  makeStyles,
  Paper,
  ThemeProvider,
  AppBar,
  unstable_createMuiStrictModeTheme as createMuiTheme,
} from "@material-ui/core";
import Navigation from "../components/Navigation";
import { BrowserRouter as Router } from "react-router-dom";
import RouteToPage from "../components/Route";
import clsx from "clsx";

const drawerWidth = 280;

const theme = createMuiTheme({
  palette: {
    primary: {
      main: "#179c7d",
    },
  },
});

const useStyles = makeStyles((theme) => ({
  seperator: {
    ...theme.mixins.toolbar,
  },
  content: {
    width: "100%",
    margin: "auto",
    minHeight: "100vh",
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.easeIn,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  appBar: {
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
}));

function App() {
  const [open, setOpen] = React.useState(true);
  const classes = useStyles();

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Navigation open={open} setOpen={setOpen} />
        <AppBar position="fixed" />
        <div className={clsx(classes.appBar, {
            [classes.appBarShift]: open,
          })}>
          <Paper className={classes.content}>
            <div className={classes.seperator} />
            <RouteToPage />
          </Paper>
        </div>
      </Router>
    </ThemeProvider>
  );
}

export default App;
