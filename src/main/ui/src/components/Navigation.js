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
  Divider,
  Typography,
  ListItemText,
  Avatar,
  CssBaseline,
  withStyles,
} from "@material-ui/core";
import { Link } from "react-router-dom";
import logo from "../logo.png";
import profile_img from "../images/John.jpg";
import MuiListItem from "@material-ui/core/ListItem";
import { Grid } from "@material-ui/core";

import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import SwapHorizIcon from "@material-ui/icons/SwapHoriz";
import PostAddIcon from "@material-ui/icons/PostAdd";
import LockOpenIcon from "@material-ui/icons/LockOpen";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import DeleteIcon from "@material-ui/icons/Delete";
import EnhancedEncryptionIcon from "@material-ui/icons/EnhancedEncryption";
import AssignmentIcon from "@material-ui/icons/Assignment";
import NotificationsActiveIcon from "@material-ui/icons/NotificationsActive";
import ShareIcon from "@material-ui/icons/Share";
import EqualizerIcon from "@material-ui/icons/Equalizer";
import MenuIcon from "@material-ui/icons/Menu";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";

var drawerWidth = 280;

const useStyles = makeStyles((theme) => ({
  root: {
  "& .appBarShift": {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  },
  appBar: {
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
  },
  drawerTitle: {
    marginLeft: "auto",
    marginRight: "auto",
    fontWeight: "bold",
  },
  drawerFooter: {
    position: "absolute",
    left: "50%",
    marginLeft: "-80px",
    width: "200px",
    bottom: "10px",
  },
  listItemSelected: {
    backgroundColor: "#ff0000",
  },
  loginBtn: {
    marginLeft: "0.5em",
  },
  divider: {
    marginTop: "0.5em",
    marginBottom: "0.5em",
    background: "rgb(23,156,125,0.4)",
    height: "2px",
  },
  profileImg: {
    marginLeft: "0.5em",
    width: theme.spacing(6),
    height: theme.spacing(6),
  },
  accountInformation: {
    height: theme.spacing(12),
  },
  toolbar: {
    widows:"100%",
  }
}));

const ListItem = withStyles((theme) => ({
  root: {
    "&$selected": {
      backgroundColor: "red",
      color: "white",
    },
    "&$selected:hover": {
      backgroundColor: theme.palette.primary.main,
      color: "white",
    },
    "&:hover": {
      backgroundColor: theme.palette.primary.main,
      color: "white",
    },
  },
  selected: {},
}))(MuiListItem);

export default function Navigation(props) {
  const { open, setOpen } = props;
  const classes = useStyles();
  const [logedIn, setLogedIn] = React.useState(true);
  const handleDrawerOpen = () => {
    setOpen(!open);
  };

  React.useEffect(() => {
    function handleResize() {
      console.log('resized to: ', window.innerWidth, 'x', window.innerHeight)
      setOpen(window.innerWidth > 600)
    }

    window.addEventListener('resize', handleResize)
  })
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
              color="inherit"
              aria-label="open drawer"
              onClick={handleDrawerOpen}
              edge="start"
              className={clsx(classes.menuButton, open && classes.hide), classes.toolbar}
            >
              <MenuIcon />
            </Button>
            <Button
              variant="text"
              color="primary"
              component={Link}
              to="/"
              className={classes.toolbar}
              startIcon={<img src={logo} alt="" />}
            />
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
          <Grid
            container
            spacing={3}
            className={classes.accountInformation}
            alignItems="center"
            justify="center"
          >
            {logedIn ? (
              <>
                <Grid item xs={2}>
                  <Avatar src={profile_img} className={classes.profileImg} />
                </Grid>
                <Grid item xs={10}>
                  <IconButton
                    color="inherit"
                    aria-label="open drawer"
                    edge="start"
                    className={classes.loginBtn}
                    component={Link}
                    to="/account"
                    onClick={() => {
                      //setOpen(!open);
                    }}
                  >
                    <Typography variant="h6"> John Voss</Typography>
                    <KeyboardArrowDownIcon />
                  </IconButton>
                </Grid>
              </>
            ) : (
              <>
                <Button
                  variant="contained"
                  color="primary"
                  component={Link}
                  to="/login"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  Sign in
                </Button>
              </>
            )}
          </Grid>

          {logedIn ? (
            <>
              <Divider className={classes.divider} />
              <Typography variant="h6" className={classes.drawerTitle}>
                {" "}
                IDS POLICY EDITORS
              </Typography>
              <List>
                <ListItem
                  button
                  component={Link}
                  to="/policy/InterpretOdrlPolicy"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <SwapHorizIcon />
                  </ListItemIcon>
                  <ListItemText>Interprete an ODRL Policy </ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  to="/policy/ComplexPolicyForm"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <PostAddIcon />
                  </ListItemIcon>
                  <ListItemText>Create Policy</ListItemText>
                </ListItem>

                <Divider className={classes.divider} />

                <ListItem
                  button
                  component={Link}
                  to="/policy/ProvideAccessPolicyForm"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <LockOpenIcon />
                  </ListItemIcon>
                  <ListItemText>Provide Access</ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  id="CountAccess"
                  to="/policy/CountAccessPolicyForm"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <EqualizerIcon />
                  </ListItemIcon>
                  <ListItemText>Count Access</ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  to="/policy/DeletData"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <DeleteIcon />
                  </ListItemIcon>
                  <ListItemText>Delete Data After</ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  to="/policy/AnonymizeInRestPolicyForm"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <EnhancedEncryptionIcon />
                  </ListItemIcon>
                  <ListItemText>Anonymize in Rest</ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  to="/policy/AnonymizeInTransitPolicyForm"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <EnhancedEncryptionIcon />
                  </ListItemIcon>
                  <ListItemText>Anonymize in Transit</ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  to="/policy/LogAccessPolicyForm"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <AssignmentIcon />
                  </ListItemIcon>
                  <ListItemText>Log Access</ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  to="/policy/InformPolicyForm"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <NotificationsActiveIcon />
                  </ListItemIcon>
                  <ListItemText>Inform Party</ListItemText>
                </ListItem>

                <ListItem
                  button
                  component={Link}
                  to="/policy/DistributeData"
                  onClick={() => {
                    //setOpen(!open);
                  }}
                >
                  <ListItemIcon>
                    <ShareIcon />
                  </ListItemIcon>
                  <ListItemText>Distribute Data</ListItemText>
                </ListItem>
              </List>
              <Typography variant="body2" className={classes.drawerFooter}>
                © {new Date().getFullYear()} Fraunhofer IESE
              </Typography>
            </>
          ) : null}
        </DrawerMui>
      </div>
    </ClickAwayListener>
  );
}
