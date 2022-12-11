/**
 * @file This contains the navigation component
 * @author Tom Kollmer 
 */

import React from "react";
import clsx from "clsx";
import {
  makeStyles,
  Toolbar,
  Button,
  AppBar as AppBarMui,
  Drawer as DrawerMui,
  List,
  IconButton,
  Typography,
  ListItemText,
  Avatar,
  CssBaseline,
} from "@material-ui/core";
import { Link } from "react-router-dom";
import logo from "../images/fhg.svg";
import profile_img from "../images/John.jpg";

import SearchIcon from '@material-ui/icons/Search';
import PostAddIcon from "@material-ui/icons/PostAdd";
import LockOpenIcon from "@material-ui/icons/LockOpen";
import DeleteIcon from "@material-ui/icons/Delete";
import EnhancedEncryptionIcon from "@material-ui/icons/EnhancedEncryption";
import AssignmentIcon from "@material-ui/icons/Assignment";
import NotificationsActiveIcon from "@material-ui/icons/NotificationsActive";
import ShareIcon from "@material-ui/icons/Share";
import EqualizerIcon from "@material-ui/icons/Equalizer";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import MenuItem from "@material-ui/core/MenuItem";
import BallotIcon from '@material-ui/icons/Ballot';
import iconSet from "../icons/selection.json";
import IcomoonReact from "icomoon-react";
import MergeTypeIcon from '@material-ui/icons/MergeType';
import "../index.css";
var drawerWidth = 324;

const useStyles = makeStyles((theme) => ({
  rightToolbar: {
    marginLeft: "auto",
    marginRight: -12,
  },
  leftToolbar: {
    marginLeft: "120px",
  },
  drawerPaper: {
    width: drawerWidth,
    marginTop: "80px",
    backgroundColor: theme.palette.primary.main,
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  navigation: {
    fontSize: "19px",
    fontFamily: "FrutigerLTComBold",
  },
  logo: {
    width: "205px",
    height: "auto",
    verticalAlign: "middle",
  },
  listitemicon: {
    marginLeft: "30px",
    marginRight: "30px",
    width: "30px",
    height: "30px",
  },
  logoPosition: {
    marginLeft: "60px",
  },
  root: {
    "& .MuiIconButton-root": {
      width: "55px",
      height: "55px",
    },
    "& .MuiToolbar-root": {
      height: "80px",
      backgroundColor: theme.palette.background.main,
    },
    "& .MuiToolbar-gutters": {
      paddingLeft: "0px",
    },
    "& .MuiListItem-root": {
      height: "75px",
      color: theme.palette.primary.light,
      fontFamily: "FrutigerLTComBold",
      fontSize: "1rem",
    },
    "& .MuiListItem-root.Mui-selected": {
      backgroundColor: theme.palette.primary.light,
      color: theme.palette.secondary.main,
    },
    "& .MuiListItem-root.Mui-selected:hover": {
      backgroundColor: theme.palette.primary.light,
      color: theme.palette.secondary.main,
    },
  },
}));

/**
 * This component is used to show the toolbar and navigation menu. 
 * The menu can also be minimized
 * @param {object} open react hook that contains a boolean if the navigation is open
 * @param {func} setOpen function to set the state of open
 * @returns 
 */
export default function Navigation(props) {
  const { open, setOpen } = props;
  const classes = useStyles();
  const [logedIn] = React.useState(true);
  const [selected, setSelected] = React.useState({ selected: "" });

  console.log(selected)
  React.useEffect(() => {
    //const parsedSelected = String(localStorage.getItem("selected") || "")
    //setSelected({ selected: parsedSelected });
  }, [])

  React.useEffect(() => {
    localStorage.setItem("selected", selected.selected)
  }, [selected.selected])

  const updateSelected = (selectedIndex) => {
    setSelected({ selected: selectedIndex });
  };

  React.useEffect(() => {
    function handleResize() {
      setOpen(window.innerWidth > 600);
    }

    window.addEventListener("resize", handleResize);
  });
  const handleClickAway = () => {
    //setOpen(false);
  };
  return (
    <ClickAwayListener onClickAway={handleClickAway}>
      <div className={classes.root}>
        <CssBaseline />
        <AppBarMui
          position="fixed"
          className={clsx(classes.appBar, {
            [classes.appBarShift]: open,
          })}
        >
          <Toolbar>
            <Button
              variant="text"
              color="primary"
              onClick={() => updateSelected("")}
              component={Link}
              to="/"
              className={classes.logoPosition}
              startIcon={<img src={logo} alt="" className={classes.logo} />}
            />
            <section className={classes.leftToolbar}>
              <Typography variant="h1"> {selected.selected} </Typography>
            </section>

            <section className={classes.rightToolbar}>
              <IconButton
                color="inherit"
                aria-label="open drawer"
                onClick={() => updateSelected("")}
                edge="start"
                className={classes.loginBtn}
                component={Link}
                to="/account"
              >
                <Avatar src={profile_img} className={classes.profileImg} />
              </IconButton>
              <IconButton>
                <IcomoonReact iconSet={iconSet} color="#444" icon="ausloggen" />
              </IconButton>
            </section>
          </Toolbar>
        </AppBarMui>

        <DrawerMui
          className={classes.drawer}
          variant="persistent"
          anchor="left"
          open={open}
          classes={{
            paper: classes.drawerPaper,
          }}
        >
          {logedIn ? (
            <>
              <List>
              <MenuItem
                  button
                  component={Link}
                  to="/policy/NegotiatePolicy"
                  onClick={() => updateSelected("Negotiate Policy")}
                  selected={selected.selected === "Negotiate Policy"}
                  className={classes.menuItem}
                >
                  <MergeTypeIcon className={classes.listitemicon} />

                  <ListItemText classes={{ primary: classes.navigation }}>
                    Negotiate Policy
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/InterpretOdrlPolicy"
                  onClick={() => updateSelected("Interprete an IDS Policy")}
                  selected={selected.selected === "Interprete an IDS Policy"}
                  className={classes.menuItem}
                >
                  <SearchIcon className={classes.listitemicon} />

                  <ListItemText classes={{ primary: classes.navigation }}>
                    Interprete Policy
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/ComplexPolicyForm"
                  onClick={() => updateSelected("Create Policy")}
                  selected={selected.selected === "Create Policy"}
                  className={classes.menuItem}
                >
                  <PostAddIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Create Policy
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/ManagePolicies"
                  onClick={() => updateSelected("Manage Policies")}
                  selected={selected.selected === "Manage Policies"}
                  className={classes.menuItem}
                >
                  <BallotIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Manage Policies
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/ProvideAccessPolicyForm"
                  onClick={() => updateSelected("Provide Access")}
                  selected={selected.selected === "Provide Access"}
                  className={classes.menuItem}
                >
                  <LockOpenIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Provide Access
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  id="CountAccess"
                  to="/policy/CountAccessPolicyForm"
                  onClick={() => updateSelected("Count Access")}
                  selected={selected.selected === "Count Access"}
                  className={classes.menuItem}
                >
                  <EqualizerIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Count Access
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/DeleteData"
                  onClick={() => updateSelected("Delete Data After")}
                  selected={selected.selected === "Delete Data After"}
                  className={classes.menuItem}
                >
                  <DeleteIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Delete Data After
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/AnonymizeInRestPolicyForm"
                  onClick={() => updateSelected("Anonymize in Rest")}
                  selected={selected.selected === "Anonymize in Rest"}
                  className={classes.menuItem}
                >
                  <EnhancedEncryptionIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Anonymize in Rest
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/AnonymizeInTransitPolicyForm"
                  onClick={() => updateSelected("Anonymize in Transit")}
                  selected={selected.selected === "Anonymize in Transit"}
                  className={classes.menuItem}
                >
                  <EnhancedEncryptionIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Anonymize in Transit
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/LogAccessPolicyForm"
                  onClick={() => updateSelected("Log Access")}
                  selected={selected.selected === "Log Access"}
                  className={classes.menuItem}
                >
                  <AssignmentIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Log Access
                  </ListItemText>
                </MenuItem>

                <MenuItem
                  button
                  component={Link}
                  to="/policy/InformPolicyForm"
                  onClick={() => updateSelected("Inform Party")}
                  selected={selected.selected === "Inform Party"}
                  className={classes.menuItem}
                >
                  <NotificationsActiveIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Inform Party
                  </ListItemText>
                </MenuItem>
                
                <MenuItem
                  button
                  component={Link}
                  to="/policy/DistributeData"
                  onClick={() => updateSelected("Distribute Data")}
                  selected={selected.selected === "Distribute Data"}
                  className={classes.menuItem}
                >
                  <ShareIcon className={classes.listitemicon} />
                  <ListItemText classes={{ primary: classes.navigation }}>
                    Distribute Data
                  </ListItemText>
                </MenuItem>
                <MenuItem/>
              </List>
            </>
          ) : null}
        </DrawerMui>
      </div>
    </ClickAwayListener>
  );
}
